package com.bookhub.error;
public class NullError implements ServiceError{

    @Override
    public String getMessage() {
        return "Something is null!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}
