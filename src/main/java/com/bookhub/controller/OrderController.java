package com.bookhub.controller;

import com.bookhub.model.OrderDTO;
import com.bookhub.service.BookOrderService;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/api/order")
public class OrderController {

    @Autowired
    BookOrderService bookOrderService;

    @PostMapping("/addOrder")
    public @ResponseBody Result<String> addNewOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request){
        return bookOrderService.addNewBookOrder(orderDTO.getAddrId(), orderDTO.getData(), request);
    }



}
