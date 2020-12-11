package com.bookhub.dao;

import com.bookhub.model.Address;
import com.bookhub.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartDAO extends CrudRepository<Cart, Integer> {

}
