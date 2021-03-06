package com.bookhub.controller;

import com.bookhub.model.User;
import com.bookhub.service.OSSService;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(path="/test")
public class TestController {

    @Autowired
    OSSService ossService;

    @GetMapping()
    public @ResponseBody
    String hello(){return "hello";}

    @PostMapping(path = "/uploadImage") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser (@RequestBody MultipartFile file) {
        return ossService.uploadFile(file);
    }

}
