package com.bookhub.error;

public class WrongValidateNumError implements ServiceError {


    @Override
    public String getMessage() {
        return "Wrong Validate Number!";
    }

    @Override
    public Integer getCode() {
        return 8;
    }
}
