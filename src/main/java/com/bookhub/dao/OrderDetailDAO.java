package com.bookhub.dao;

import com.bookhub.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDAO extends CrudRepository<OrderDetail, Integer> {
    Iterable<OrderDetail> getAllByBookOrder_Id(int order_id);

}
