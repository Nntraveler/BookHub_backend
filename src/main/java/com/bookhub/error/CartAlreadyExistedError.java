package com.bookhub.error;

public class CartAlreadyExistedError implements ServiceError {
    @Override
    public String getMessage() {
        return "Cart item already exists!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}
