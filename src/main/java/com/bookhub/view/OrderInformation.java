package com.bookhub.view;

import com.bookhub.dao.AddressDAO;
import com.bookhub.dao.OrderDetailDAO;
import com.bookhub.model.Address;
import com.bookhub.model.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderInformation {
//    private final BookOrder bookOrder;
//
//    public OrderInformation(BookOrder bookOrder) {
//        this.bookOrder = bookOrder;
//    }
//
//    public int getOrderId(){
//        return bookOrder.getId();
//    }
//    public String getName(){
//        return bookOrder.addressInstance().getName();
//    }
//    public String getLocation(){
//        return bookOrder.addressInstance().getLocation();
//    }
//    public String getOrderStatus(){
//        return bookOrder.getStatus();
//    }
//    public Double getTotal(){
//        return bookOrder.getPrice();
//    }

    @Autowired
    AddressDAO addressDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;

    private int orderId;
    private String name;
    private int phone;
    private String location;
    private List<BookListInformation> bookList;
    private String startTime;
    private String receiveTime;
    private Number postCost;
    private Number total;
    private String orderStaus;

    public OrderInformation(BookOrder bookOrder) {
        this.orderId = bookOrder.getId();

        Address address = bookOrder.addressInstance();
        this.name = address.getName();
        this.phone = Integer.parseInt(address.getPhone());
        this.location = address.getLocation();

        this.startTime = bookOrder.getStartTime().toString();
        this.receiveTime = bookOrder.getReceiveTime().toString();
        this.postCost = bookOrder.getPostCost();
        this.total = bookOrder.getPrice();
        this.orderStaus = bookOrder.getStatus();

        BookListInformation bookListInformation = new BookListInformation();
        this.bookList = bookListInformation.getList(this.orderId);

    }
}
