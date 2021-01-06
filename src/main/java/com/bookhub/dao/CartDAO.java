package com.bookhub.dao;

import com.bookhub.model.Address;
import com.bookhub.model.Book;
import com.bookhub.model.Cart;
import com.bookhub.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartDAO extends CrudRepository<Cart, Integer> {
    Optional<Cart> findCartByBook_IdAndOwner_Id(int bookId, String ownerId);

}
