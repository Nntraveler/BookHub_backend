package com.bookhub.controller;

import com.bookhub.model.Address;
import com.bookhub.model.User;
import com.bookhub.service.AddressService;
import com.bookhub.util.Request;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="/addresses") // This means URL's start with /demo (after Application path)
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping
    public @ResponseBody Result<String> addNewAddress (@RequestBody Request<Address> request) {
        return addressService.addNewAddress(request.getSessionId(),request.getData());
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Address> getAddress (@RequestParam String sessionId,@PathVariable Integer id) {
        return addressService.getAddress(sessionId,id);
    }

    @GetMapping
    public @ResponseBody Result<Set<Address>> getAllAddress (@RequestParam String sessionId) {
        return addressService.getAllAddress(sessionId);
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<String> updateAddress (@RequestBody Request<Address> request,@PathVariable Integer id){
        return addressService.updateAddress(request.getData(),id,request.getSessionId());
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Result<String> deleteAddress (@RequestBody String sessionId,@PathVariable Integer id){
        return addressService.deleteAddress(id,sessionId);
    }
}
