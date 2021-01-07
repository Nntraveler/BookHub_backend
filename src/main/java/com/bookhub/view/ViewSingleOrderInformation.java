package com.bookhub.view;

import com.bookhub.model.BookOrder;

public class ViewSingleOrderInformation {
    private BookOrder bookOrder;
    public ViewSingleOrderInformation(BookOrder bookOrder){
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
    public Number getTotal(){
        return bookOrder.getPrice();
    }
}
