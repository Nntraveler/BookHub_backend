package com.bookhub.dao;

import com.bookhub.model.Book;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookDAO extends CrudRepository<Book, Integer> {

}