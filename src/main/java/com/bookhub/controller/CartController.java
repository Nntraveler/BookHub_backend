package com.bookhub.controller;

import com.bookhub.model.PurchaseDTO;
import com.bookhub.service.CartService;
import com.bookhub.util.Result;
import com.bookhub.view.CartInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping(path="/CheckCart")
    public @ResponseBody Result<List<CartInformation>> getCartItems(HttpServletRequest request){
        return cartService.getItemsInCart(request);
    }

    @PostMapping(path="/AddCart")
    public @ResponseBody Result<String> addItem(@RequestBody PurchaseDTO purchaseDTO, HttpServletRequest request){
        return cartService.addItem(purchaseDTO, request);
    }

    @DeleteMapping(path="/DelCart")
    public @ResponseBody Result<String> deleteItem(@RequestParam Integer bookId, HttpServletRequest request){
        return cartService.deleteItem(bookId, request);
    }

    @PostMapping(path="/EditCart")
    public @ResponseBody Result<String> editItem(@RequestBody PurchaseDTO purchaseDTO, HttpServletRequest request){
        return cartService.updateItem(purchaseDTO, request);
    }


}
