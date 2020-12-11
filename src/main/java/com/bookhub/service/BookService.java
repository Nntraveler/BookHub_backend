package com.bookhub.service;

import com.bookhub.dao.BookDAO;
import com.bookhub.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    StringRedisTemplate template;


}
