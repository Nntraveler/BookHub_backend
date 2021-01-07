package com.bookhub.controller;

import com.bookhub.model.EditDTO;
import com.bookhub.model.OrderDTO;
import com.bookhub.service.BookOrderService;
import com.bookhub.util.Result;
import com.bookhub.view.OrderInformation;
import com.bookhub.view.ViewSingleOrderInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/api/order")
public class OrderController {

    @Autowired
    BookOrderService bookOrderService;

    @PostMapping("/addOrder")
    public @ResponseBody Result<String> addNewOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request){
        return bookOrderService.addNewBookOrder(orderDTO.getAddrId(), orderDTO.getData(), request);
    }

    @GetMapping("/GetOrders")
    public @ResponseBody Result <List<ViewSingleOrderInformation>> getOrders(HttpServletRequest request){
        return bookOrderService.getOrders(request);
    }

    @GetMapping("/GetOrderDetail")
    public @ResponseBody Result<OrderInformation> getOrderDetail(HttpServletRequest request, @RequestParam String orderId){
        return bookOrderService.getOrderDetail(request,orderId);
    }

    @PostMapping("EditOrder")
    public @ResponseBody Result<String> editOrder(HttpServletRequest request,@RequestBody EditDTO editDTO){
        return bookOrderService.editOrder(request,editDTO);
    }

    @DeleteMapping("DelOrder")
    public @ResponseBody Result<String> deleteOrder(HttpServletRequest request,@RequestParam String orderId){
        return bookOrderService.deleteOrder(request,orderId);
    }


}
