package com.bookhub.controller;

import com.bookhub.model.Address;
import com.bookhub.model.User;
import com.bookhub.service.AddressService;
import com.bookhub.util.Request;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="/api/addresses") // This means URL's start with /demo (after Application path)
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/AddAddr")
    public @ResponseBody Result<String> addNewAddress (@RequestBody Address address, HttpServletRequest request) {
        return addressService.addNewAddress(request,address);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Address> getAddress (HttpServletRequest request,@PathVariable Integer id) {
        return addressService.getAddress(request,id);
    }

    @GetMapping("/GetAddr")
    public @ResponseBody Result<Set<Address>> getAllAddress (HttpServletRequest request) {
        return addressService.getAllAddress(request);
    }

    @PostMapping("EditAddr/{id}")
    public @ResponseBody Result<String> updateAddress (@RequestBody Address address,@PathVariable Integer id, HttpServletRequest request){
        return addressService.updateAddress(address,id,request);
    }

    @DeleteMapping("DelAddr/{id}")
    public @ResponseBody Result<String> deleteAddress (HttpServletRequest request,@PathVariable Integer id){
        return addressService.deleteAddress(id,request);
    }
}
