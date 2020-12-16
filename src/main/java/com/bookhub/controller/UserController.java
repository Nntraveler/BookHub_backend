package com.bookhub.controller;

import com.bookhub.model.User;
import com.bookhub.service.UserService;
import com.bookhub.util.Request;
import com.bookhub.util.Result;
import com.bookhub.view.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller // This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewUser (@RequestBody User user) {
       return userService.addNewUser(user);
    }

    @GetMapping
    public @ResponseBody Result<UserInformation> getUser (@RequestParam String sessionId) {
       return userService.getUser(sessionId);
    }

    @PutMapping
    public @ResponseBody Result<UserInformation> updateUser(@RequestBody Request<User> request){
        return userService.updateUser(request);
    }

    @PostMapping(path="/session")
    public @ResponseBody Result<String> getLoginToken(@RequestBody User user) {
        return userService.getLoginToken(user);
    }

    @DeleteMapping(path="/session")
    public @ResponseBody Result<String> invalidateSessionId(@RequestParam String sessionId) {
       return userService.invalidateSessionId(sessionId);
    }

    @PostMapping(path="/validation")
    public @ResponseBody Result<String> sendEmail(@RequestParam String sessionId) {
       return userService.sendEmail(sessionId);
    }

    @PutMapping(path="/validation")
    public @ResponseBody Result<String> validateAccount(@RequestBody Request<String> request){
        return userService.validateAccount(request);
    }
}
