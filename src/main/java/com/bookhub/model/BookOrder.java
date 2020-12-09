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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name="address_id")
    private Address address;

}
