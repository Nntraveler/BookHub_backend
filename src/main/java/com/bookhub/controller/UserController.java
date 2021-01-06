package com.bookhub.controller;

import com.bookhub.model.User;
import com.bookhub.service.UserService;
import com.bookhub.util.Request;
import com.bookhub.util.Result;
import com.bookhub.view.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller // This means that this class is a Controller
@RequestMapping(path="/api/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register") // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewUser (@RequestBody User user) {
       return userService.addNewUser(user);
    }

    @GetMapping
    public @ResponseBody Result<UserInformation> getUser (HttpServletRequest request) {
       return userService.getUser(request);
    }

    @PutMapping
    public @ResponseBody Result<UserInformation> updateUser(@RequestBody Request<User> requestBody, HttpServletRequest request){
        return userService.updateUser(requestBody, request);
    }

    @PostMapping(path="/login")
    public @ResponseBody Result<String> getLoginToken(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.getLoginToken(user, request ,response);
    }

    @DeleteMapping(path="/logout")
    public @ResponseBody Result<String> invalidateSessionId(HttpServletRequest request, HttpServletResponse response) {
       return userService.invalidateSessionId(request, response);
    }

    @PostMapping(path="/validation")
    public @ResponseBody Result<String> sendEmail(HttpServletRequest request) {
       return userService.sendEmail(request);
    }

    @PutMapping(path="/validation")
    public @ResponseBody Result<String> validateAccount(@RequestBody Request<String> requestBody, HttpServletRequest request){
        return userService.validateAccount(requestBody, request);
    }

    @GetMapping(path="/info")
    public @ResponseBody Result<UserInformation> getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }
}
