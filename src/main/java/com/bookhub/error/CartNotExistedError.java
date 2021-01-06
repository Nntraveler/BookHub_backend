package com.bookhub.error;

public class CartNotExistedError implements ServiceError {
    @Override
    public String getMessage() {
        return "Cart item isn't existed!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}
