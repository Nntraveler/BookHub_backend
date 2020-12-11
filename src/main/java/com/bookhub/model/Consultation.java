package com.bookhub.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String status;

    @OneToMany(mappedBy = "consultation",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<ConsultDetail> consultDetailSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="staff_id")
    private User serviceStaff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private User customer;


}
