package com.bookhub.service;

import com.bookhub.dao.BookDAO;
import com.bookhub.dao.CartDAO;
import com.bookhub.dao.UserDAO;
import com.bookhub.error.*;
import com.bookhub.model.*;
import com.bookhub.util.Result;
import com.bookhub.util.SessionValidator;
import com.bookhub.view.CartInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {

    @Autowired
    CartDAO cartDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    StringRedisTemplate template;

    public String resolveSessionIDInCookie(HttpServletRequest request){
        return SessionValidator.validateSessionID(request, template);
    }

    @Transactional
    public Result<String> addItem(CartDTO cartDTO, HttpServletRequest request){
        String userId = resolveSessionIDInCookie(request);
        if(userId==null || userId.equals("")) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Cart> optionalCart = cartDAO.findCartByBook_IdAndOwner_Id(cartDTO.getBookId(), userId);
        if(optionalCart.isPresent()){
            return Result.wrapErrorResult(new CartAlreadyExistedError());
        }
        User user=new User();
        user.setId(userId);
        Optional<Book> optionalBook = bookDAO.findById(cartDTO.getBookId());
        if(!optionalBook.isPresent()) return Result.wrapErrorResult(new BookNotExistedError());
        Book book = optionalBook.get();
        book.setId(cartDTO.getBookId());
        Cart cart = new Cart();
        cart.setQuantity(cartDTO.getQuantity());
        cart.setBook(book);
        cart.setOwner(user);
        cartDAO.save(cart);
        return Result.wrapSuccessfulResult("Saved");
    }

    @Transactional
    public Result<String> deleteItem(Integer bookId, HttpServletRequest request){
        String userId = resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Cart> optionalCart=cartDAO.findCartByBook_IdAndOwner_Id(bookId, userId);
        if(!optionalCart.isPresent()) return Result.wrapErrorResult(new CartNotExistedError());
        if(!userId.equals(optionalCart.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        cartDAO.delete(optionalCart.get());
        return Result.wrapSuccessfulResult("Deleted");
    }

    public Result<List<CartInformation>> getItemsInCart(HttpServletRequest request){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return  Result.wrapErrorResult(new UserNotExistedError());
        Set<Cart> carts = optionalUser.get().cartSetInstance();
        List<CartInformation> cartInformation = new ArrayList<>();
        for(Cart cart: carts){
            cartInformation.add(new CartInformation(cart));
        }
        return Result.wrapSuccessfulResult(cartInformation);
    }

    @Transactional
    public Result<String> updateItem(CartDTO cartDTO, HttpServletRequest request){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return  Result.wrapErrorResult(new UserNotExistedError());
        Optional<Cart> optionalCart = cartDAO.findCartByBook_IdAndOwner_Id(cartDTO.getBookId(), userId);
        if(!optionalCart.isPresent()) return Result.wrapErrorResult(new CartNotExistedError());
        if(!userId.equals(optionalCart.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        optionalCart.get().setQuantity(cartDTO.getQuantity());
        cartDAO.save(optionalCart.get());
        return Result.wrapSuccessfulResult("Updated");
    }

}
