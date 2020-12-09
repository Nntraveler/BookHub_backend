package com.bookhub.service;

import com.bookhub.dao.BookOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderService {

    @Autowired
    BookOrderDAO bookOrderDAO;

}
