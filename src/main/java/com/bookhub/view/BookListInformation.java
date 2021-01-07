package com.bookhub.view;

import com.bookhub.dao.BookOrderDAO;
import com.bookhub.error.BookOrderNotExistedError;
import com.bookhub.model.BookOrder;
import com.bookhub.model.OrderDetail;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookListInformation {

    private int bookId;
    private String bookName;
    private int quantity;
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
