package com.bookhub.view;

import com.bookhub.model.Book;



public class BookInformation {

    private final Book book;

    public BookInformation(Book book) {
        this.book = book;
    }

    public Integer getId() {
        return book.getId();
    }

    public String getISBN() {
        return book.getISBN();
    }

    public String getName() {
        return book.getName();
    }

    public String getPublisher() {
        return book.getPublisher();
    }

    public String getBookAbstract() {
        return book.getBookAbstract();
    }

    public String getAuthor() {
        return book.getAuthor();
    }

    public String getCategory() {
        return book.getCategory();
    }

    public Double getPrice() {
        return book.getPrice();
    }

    public Double getDiscount() {
        return book.getDiscount();
    }

    public Boolean getSecondhand() {
        return book.getSecondhand();
    }

    public String getImage(){ return  book.getImage();}

}
