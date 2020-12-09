package com.bookhub.model;

import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String phone;

    private String country;

    private String province;

    private String city;

    private String district;

    @Column(name="post_code")
    private Integer postCode;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "address",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<BookOrder> bookOrderSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner.getId();
    }

    public User ownerInstance() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<BookOrder> BookOrderSetInstance() {
        return bookOrderSet;
    }

    public void setBookOrderSet(Set<BookOrder> bookOrderSet) {
        this.bookOrderSet = bookOrderSet;
    }
}
