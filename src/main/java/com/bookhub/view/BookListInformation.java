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
    @Autowired
    BookOrderDAO bookOrderDAO;


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

    public List<BookListInformation> getList(int orderId){

        Optional<BookOrder> optionalBookOrder = bookOrderDAO.findById(orderId);
        if(!optionalBookOrder.isPresent()) return (List<BookListInformation>) Result.wrapErrorResult(new BookOrderNotExistedError());
        List<BookListInformation> bookListInformationList = new ArrayList<>();
        Set<OrderDetail> orderDetailSet = optionalBookOrder.get().orderDetailSetInstance();
        for (OrderDetail orderDetail: orderDetailSet ) {
            BookListInformation bookListInformation = new BookListInformation();
            bookListInformation.setBookId(orderDetail.getBook());
            bookListInformation.setBookName(orderDetail.bookInstance().getName());
            bookListInformation.setQuantity(orderDetail.getQuantity());

            bookListInformationList.add(bookListInformation);
        }

        return bookListInformationList;
    }

}
