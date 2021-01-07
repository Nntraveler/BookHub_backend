package com.bookhub.service;

import com.bookhub.dao.BookDAO;
import com.bookhub.dao.UserDAO;
import com.bookhub.error.BookNotExistedError;
import com.bookhub.error.InvalidSessionIdError;
import com.bookhub.error.PermissionDeniedError;
import com.bookhub.error.UserNotExistedError;
import com.bookhub.model.Book;
import com.bookhub.model.User;
import com.bookhub.util.Result;
import com.bookhub.util.Role;
import com.bookhub.view.BookInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
//import sun.rmi.log.LogInputStream;

import java.util.*;

@Service
public class BookService {

    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    StringRedisTemplate template;

    @Autowired
    OSSService ossService;

    @Transactional
    public Result<String> addNewBook (String sessionId, Book book) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        if(!optionalUser.get().getAuthority().equals(Role.INVENTORY_MANAGER.ordinal()))
            return Result.wrapErrorResult(new PermissionDeniedError());
        book.setImage(null);
        bookDAO.save(book);
        return Result.wrapSuccessfulResult("Saved");
    }

    public Result<BookInformation> getBookInformation (Integer id) {
        Optional<Book> optionalBook=bookDAO.findById(id);
        if(!optionalBook.isPresent()) return  Result.wrapErrorResult(new BookNotExistedError());
        return Result.wrapSuccessfulResult(new BookInformation(optionalBook.get()));
    }

    public Result<List<BookInformation>> searchBookByName (String name) {
        List<Book> books=bookDAO.findBooksByNameLike(name);
        List<BookInformation> bookInformationList= new LinkedList<>();
        for(Book book: books){
            bookInformationList.add(new BookInformation(book));
        }
        return Result.wrapSuccessfulResult(bookInformationList);
    }


    public Result<List<BookInformation>> getBookInformationByPage(Integer pageNum, Integer pageSize){
        if(pageNum==null){
            pageNum=1;
        }

        if(pageSize==null||pageSize<=0){
            pageSize=20;
        }

        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable=PageRequest.of(pageNum-1,pageSize,sort);
        Page<Book> bookPage=bookDAO.findAll(pageable);
        List<BookInformation> bookInformationList= new LinkedList<>();
        for(Book book:bookPage){
            bookInformationList.add(new BookInformation(book));
        }
        return Result.wrapSuccessfulResult(bookInformationList);
    }

    @Transactional
    public  Result<String> updateBook (Book book, Integer id,String sessionId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        if(!optionalUser.get().getAuthority().equals(Role.INVENTORY_MANAGER.ordinal()))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Optional<Book> optionalBook=bookDAO.findById(id);
        if(!optionalBook.isPresent()) return Result.wrapErrorResult(new BookNotExistedError());
        book.setId(id);
        book.setImage(optionalBook.get().getImage());
        bookDAO.save(book);
        return Result.wrapSuccessfulResult("Updated");
    }

    @Transactional
    public  Result<String> deleteBook (Integer id,String sessionId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        if(!optionalUser.get().getAuthority().equals(Role.INVENTORY_MANAGER.ordinal()))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Optional<Book> optionalBook=bookDAO.findById(id);
        if(!optionalBook.isPresent()) return Result.wrapErrorResult(new BookNotExistedError());
        bookDAO.delete(optionalBook.get());
        return Result.wrapSuccessfulResult("Deleted");
    }

    @Transactional
    public Result<BookInformation> uploadImage (Integer id, String sessionId, MultipartFile file) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());
        if(!optionalUser.get().getAuthority().equals(Role.INVENTORY_MANAGER.ordinal()))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Optional<Book> optionalBook=bookDAO.findById(id);
        if(!optionalBook.isPresent()) return  Result.wrapErrorResult(new BookNotExistedError());
        String imageUrl=ossService.uploadFile(file);
        optionalBook.get().setImage(imageUrl);
        bookDAO.save(optionalBook.get());
        return Result.wrapSuccessfulResult(new BookInformation(optionalBook.get()));
    }

}
