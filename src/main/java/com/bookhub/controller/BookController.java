package com.bookhub.controller;

import com.bookhub.model.Book;
import com.bookhub.service.BookService;
import com.bookhub.util.Result;
import com.bookhub.view.BookInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/GetAllBook")
    public @ResponseBody
    Result<List<BookInformation>> getAllBook(){
        return bookService.getBookInformationByPage(1, 20);
    }

    @GetMapping("/GetBookDetail")
    public @ResponseBody
    Result<BookInformation> getAllBook(@RequestParam int bookId){
        return bookService.getBookInformation(bookId);
    }

    @GetMapping("/SearchBook")
    public @ResponseBody
    Result<List<BookInformation>> searchBook(@RequestParam String query){
        return bookService.searchBookByName("%" + query + "%");
    }

}
