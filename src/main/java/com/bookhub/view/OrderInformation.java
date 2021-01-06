package com.bookhub.view;

import com.bookhub.model.BookOrder;

public class OrderInformation {
    private final BookOrder bookOrder;

    public OrderInformation(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
    }

    public int getOrderId(){
        return bookOrder.getId();
    }
    public String getName(){
        return bookOrder.addressInstance().getName();
    }
    public String getLocation(){
        return bookOrder.addressInstance().getLocation();
    }
    public String getOrderStatus(){
        return bookOrder.getStatus();
    }
    public Double getTotal(){
        return bookOrder.getPrice();
    }
}
