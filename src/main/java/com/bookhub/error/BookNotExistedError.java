package com.bookhub.error;

public class BookNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Book isn't existed!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}
