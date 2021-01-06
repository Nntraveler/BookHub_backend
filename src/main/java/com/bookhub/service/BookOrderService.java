package com.bookhub.service;

import com.bookhub.dao.AddressDAO;
import com.bookhub.dao.BookDAO;
import com.bookhub.dao.BookOrderDAO;
import com.bookhub.dao.OrderDetailDAO;
import com.bookhub.error.AddressNotExistedError;
import com.bookhub.error.BookNotExistedError;
import com.bookhub.error.InvalidSessionIdError;
import com.bookhub.error.PermissionDeniedError;
import com.bookhub.model.*;
import com.bookhub.util.Result;
import com.bookhub.util.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookOrderService {

    @Autowired
    BookOrderDAO bookOrderDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    StringRedisTemplate template;

    public String resolveSessionIDInCookie(HttpServletRequest request){
        return SessionValidator.validateSessionID(request, template);
    }

    @Transactional
    public Result<String> addNewBookOrder(int addressId, List<PurchaseDTO> purchaseDTOList, HttpServletRequest request){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Address> optionalAddress = addressDAO.findById(addressId);
        if(!optionalAddress.isPresent()) return Result.wrapErrorResult(new AddressNotExistedError());
        if(!userId.equals(optionalAddress.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        BookOrder bookOrder = new BookOrder();
        int total_quantity = 0;
        Double total_price = 0.0d;
        for(PurchaseDTO purchaseDTO : purchaseDTOList){

        }
        bookOrder.setAddress(optionalAddress.get());
        bookOrder.setPostCost(0.0d);
        bookOrder.setStartTime(new Date());
        bookOrder.setStatus("unpaid");
        Set<OrderDetail> orderDetails = new HashSet<>();
        for(PurchaseDTO purchaseDTO : purchaseDTOList){

            OrderDetail orderDetail = new OrderDetail();
            Optional<Book> optionalBook = bookDAO.findById(purchaseDTO.getBookId());
            if(!optionalBook.isPresent()){
                return Result.wrapErrorResult(new BookNotExistedError());
            }
            total_quantity += purchaseDTO.getQuantity();
            Book book = optionalBook.get();
            total_price+=book.getPriceAfterDiscount()*purchaseDTO.getQuantity();
            orderDetail.setBook(book);
            orderDetail.setPrice(book.getPriceAfterDiscount());
            orderDetail.setQuantity(purchaseDTO.getQuantity());
            orderDetail.setBookOrder(bookOrder);
            orderDetails.add(orderDetail);
        }
        bookOrder.setPrice(total_price);
        bookOrder.setQuantity(total_quantity);
        bookOrder.setOrderDetailSet(orderDetails);
        bookOrderDAO.save(bookOrder);
        for(OrderDetail orderDetail: orderDetails){
            orderDetailDAO.save(orderDetail);
        }
        return Result.wrapSuccessfulResult("Saved");
    }


}
