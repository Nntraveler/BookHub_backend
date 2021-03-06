package com.bookhub.dao;

import com.bookhub.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookDAO extends CrudRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);
    List<Book> findBooksByNameLike(String name);
}