package com.bookhub.view;

import com.bookhub.model.Cart;

public class CartInformation {
    private final Cart cart;

    public CartInformation(Cart cart) {
        this.cart = cart;
    }

    public Integer getCartId(){
        return cart.getId();
    }

    public String getBookName(){
        return cart.bookInstance().getName();
    }

    public Integer getBookId() {
        return cart.getBookId();
    }

    public Integer getQuantity(){
        return cart.getQuantity();
    }

    public Double getDiscount() {
        return cart.bookInstance().getDiscount();
    }

    public Double getPrice(){
        return cart.bookInstance().getPriceAfterDiscount();
    }

    public String getImageUrl(){
        return cart.bookInstance().getImage();
    }
}
