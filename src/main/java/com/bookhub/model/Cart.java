package com.bookhub.model;

import javax.persistence.*;

@Entity
public class Cart {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    public String getOwner() {
        return owner.getId();
    }

    public User ownerInstance() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Book bookInstance(){
        return book;
    }

    public Integer getBookId() {
        return book.getId();
    }

    public String getOwnerId(){return owner.getId();}

    public void setBook(Book book) {
        this.book = book;
    }
}
