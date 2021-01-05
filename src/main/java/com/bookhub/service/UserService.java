package com.bookhub.service;

import com.bookhub.dao.UserDAO;
import com.bookhub.error.*;
import com.bookhub.model.User;
import com.bookhub.util.HashHelper;
import com.bookhub.util.Request;
import com.bookhub.util.Result;
import com.bookhub.util.SessionValidator;
import com.bookhub.view.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Value("${spring.mail.from}")
    private String mailFrom;

    @Autowired
    StringRedisTemplate template;

    @Autowired
    private JavaMailSender javaMailSender;

    public String resolveSessionIDInCookie(HttpServletRequest request){
        return SessionValidator.validateSessionID(request, template);
    }

    @Transactional
    public Result<String> addNewUser (User user) {
        Optional<User> opt=userDAO.findById(user.getId());
        if(opt.isPresent()) return Result.wrapErrorResult(new UserAlreadyExistedError());
        User n = new User();
        n.setName(user.getName());
        double seed= ThreadLocalRandom.current().nextDouble();
        n.setSalt(HashHelper.computeSha256Hash(user.getId()+ seed));
        n.setPassword(HashHelper.computeSha256Hash(user.getPassword()+n.getSalt()));
        n.setId(user.getId());
        n.setAuthority(0);
        n.setGender(user.getGender());
        n.setEmail(user.getEmail());
        n.setActive(false);
        userDAO.save(n);
        return Result.wrapSuccessfulResult("Saved");
    }

    public Result<UserInformation> getUser (HttpServletRequest request) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        String id=resolveSessionIDInCookie(request);
        if(id==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User>  optionalUser=userDAO.findById(id);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        return Result.wrapSuccessfulResult(new UserInformation(optionalUser.get()));
    }

    @Transactional
    public Result<UserInformation> updateUser(Request<User> requestBody, HttpServletRequest request){
        String id = resolveSessionIDInCookie(request);
        if(id==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(id);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        optionalUser.get().setEmail(requestBody.getData().getEmail());
        optionalUser.get().setGender(requestBody.getData().getGender());
        optionalUser.get().setName(requestBody.getData().getName());
        userDAO.save(optionalUser.get());
        return Result.wrapSuccessfulResult(new UserInformation(optionalUser.get()));

    }

    public Result<String> sendEmail(HttpServletRequest request){
        String id=resolveSessionIDInCookie(request);
        if(id==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User>  optionalUser=userDAO.findById(id);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        if(optionalUser.get().isActive()) return Result.wrapErrorResult(new AlreadyValidatedError());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(optionalUser.get().getEmail());
        message.setSubject("BookHub注册验证码");
        int validateNum=ThreadLocalRandom.current().nextInt(1000000,9999999);
        message.setText(String.valueOf(validateNum));
        javaMailSender.send(message);
        template.opsForValue()
                .set(optionalUser.get().getId()+optionalUser.get().getSalt()
                        ,String.valueOf(validateNum),10, TimeUnit.MINUTES);
        return Result.wrapSuccessfulResult("Validated Number is sent.");
    }

    public Result<String> getLoginToken(User user, HttpServletRequest request, HttpServletResponse response) {
        String sessionId;
        Optional<User> opt=userDAO.findById(user.getId());
        if(!opt.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        String passwordHashed=HashHelper.computeSha256Hash(user.getPassword()+opt.get().getSalt());
        if(!opt.get().getPassword().equals(passwordHashed)) return Result.wrapErrorResult(new WrongPasswordError());
        double seed=ThreadLocalRandom.current().nextDouble();
        sessionId=HashHelper.computeMD5Hash(user.getId()+ seed);
        if(sessionId==null) return Result.wrapErrorResult(new NullError());
        template.opsForValue().set(sessionId,user.getId(),3, TimeUnit.HOURS);
        Cookie cookie = new Cookie("sessionID", sessionId);
        cookie.setMaxAge(3*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.wrapSuccessfulResult(sessionId);
    }

    @Transactional
    public Result<String> validateAccount(Request<String> requestBody, HttpServletRequest request){
        String id = resolveSessionIDInCookie(request);
        if(id==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(id);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        String validateNum=template.opsForValue().get(optionalUser.get().getId()+optionalUser.get().getSalt());
        if(!requestBody.getData().equals(validateNum)) return Result.wrapErrorResult(new WrongValidateNumError());
        optionalUser.get().setActive(true);
        userDAO.save(optionalUser.get());
        return Result.wrapSuccessfulResult("Validate successfully!");
    }


    public Result<String> invalidateSessionId(HttpServletRequest request, HttpServletResponse response) {
        String id = resolveSessionIDInCookie(request);
        if(id==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        template.delete(id);
        Cookie cookie = new Cookie("sessionID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.wrapSuccessfulResult("logout!");
    }

}
