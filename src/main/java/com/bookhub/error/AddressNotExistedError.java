package com.bookhub.error;

public class AddressNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Address isn't existed!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}