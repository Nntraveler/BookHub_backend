package com.bookhub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer quantity;

    private Double price;

    private String status;

    private Double postCost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="receive_time")
    private Date receiveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPostCost() {
        return postCost;
    }

    public void setPostCost(Double postCost) {
        this.postCost = postCost;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Address addressInstance(){
        return address;
    }

    public Integer getAddress() {
        return address.getId();
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
