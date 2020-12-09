package com.bookhub.model;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
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

    public Integer getBook() {
        return book.getId();
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
