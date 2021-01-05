package com.bookhub.service;

import com.bookhub.dao.AddressDAO;
import com.bookhub.dao.UserDAO;
import com.bookhub.error.AddressNotExistedError;
import com.bookhub.error.InvalidSessionIdError;
import com.bookhub.error.PermissionDeniedError;
import com.bookhub.error.UserNotExistedError;
import com.bookhub.model.Address;
import com.bookhub.model.User;
import com.bookhub.util.Result;
import com.bookhub.util.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressService {

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    StringRedisTemplate template;

    public String resolveSessionIDInCookie(HttpServletRequest request){
        return SessionValidator.validateSessionID(request, template);
    }


    @Transactional
    public Result<String> addNewAddress (HttpServletRequest request, Address address) {
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        User user=new User();
        user.setId(userId);
        address.setOwner(user);
        addressDAO.save(address);
        return Result.wrapSuccessfulResult("Saved");
    }

    public Result<Address> getAddress (HttpServletRequest request, Integer id) {
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Address> optionalAddress=addressDAO.findById(id);
        if(!optionalAddress.isPresent()) return  Result.wrapErrorResult(new AddressNotExistedError());
        if(!userId.equals(optionalAddress.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalAddress.get());
    }

    public Result<Set<Address>> getAllAddress (HttpServletRequest request) {
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return  Result.wrapErrorResult(new UserNotExistedError());
        return Result.wrapSuccessfulResult(optionalUser.get().addressSetInstance());
    }

    @Transactional
    public  Result<String> updateAddress (Address address, Integer id,HttpServletRequest request) {
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        User user=new User();
        user.setId(userId);
        Optional<Address> optionalAddress=addressDAO.findById(id);
        if(!optionalAddress.isPresent()) return Result.wrapErrorResult(new AddressNotExistedError());
        if(!userId.equals(optionalAddress.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        address.setId(id);
        address.setOwner(user);
        addressDAO.save(address);
        return Result.wrapSuccessfulResult("Updated");
    }

    @Transactional
    public  Result<String> deleteAddress (Integer id,HttpServletRequest request) {
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Address> optionalAddress=addressDAO.findById(id);
        if(!optionalAddress.isPresent()) return Result.wrapErrorResult(new AddressNotExistedError());
        if(!userId.equals(optionalAddress.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        addressDAO.delete(optionalAddress.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
