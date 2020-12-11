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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ConsultDetail> consultDetailSetInstance() {
        return consultDetailSet;
    }

    public void setConsultDetailSet(Set<ConsultDetail> consultDetailSet) {
        this.consultDetailSet = consultDetailSet;
    }

    public User serviceStaffInstance() {
        return serviceStaff;
    }

    public String getServiceStaff() {
        return serviceStaff.getId();
    }

    public void setServiceStaff(User serviceStaff) {
        this.serviceStaff = serviceStaff;
    }

    public String getCustomer() {
        return customer.getId();
    }

    public User customerInstance() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
