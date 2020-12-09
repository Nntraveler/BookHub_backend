package com.bookhub.dao;

import com.bookhub.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDAO extends CrudRepository<OrderDetail, String> {

}
