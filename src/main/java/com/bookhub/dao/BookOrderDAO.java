package com.bookhub.dao;

import com.bookhub.model.BookOrder;
import org.springframework.data.repository.CrudRepository;

public interface BookOrderDAO extends CrudRepository<BookOrder, Integer> {

}
