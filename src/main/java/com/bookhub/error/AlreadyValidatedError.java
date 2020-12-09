package com.bookhub.error;

public class AlreadyValidatedError implements ServiceError {
    @Override
    public String getMessage() {
        return "Already Validated!";
    }

    @Override
    public Integer getCode() {
        return 9;
    }
}
