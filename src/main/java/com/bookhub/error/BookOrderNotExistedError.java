package com.bookhub.error;

public class BookOrderNotExistedError implements ServiceError{
    @Override
    public String getMessage() {
        return "Book Order isn't existed!";
    }

    @Override
    public Integer getCode() {
        return 403;
    }
}
