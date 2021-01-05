package com.bookhub.service;

import com.bookhub.dao.BookOrderDAO;
import com.bookhub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookOrderService {

    @Autowired
    BookOrderDAO bookOrderDAO;

    @Transactional
    Result<String> addNewBookOrder(){

        return Result.wrapSuccessfulResult("Saved");
    }


}
