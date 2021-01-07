package com.bookhub.service;

import com.bookhub.dao.*;
import com.bookhub.error.*;
import com.bookhub.model.*;
import com.bookhub.util.Result;
import com.bookhub.util.SessionValidator;
import com.bookhub.view.DetailedOrderInformation;
import com.bookhub.view.OrderInformation;
import com.bookhub.view.ViewSingleOrderInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookOrderService {
    @Autowired
    BookOrderDAO bookOrderDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    StringRedisTemplate template;

    public String resolveSessionIDInCookie(HttpServletRequest request){
        return SessionValidator.validateSessionID(request, template);
    }

    @Transactional
    public Result<String> addNewBookOrder(int addressId, List<PurchaseDTO> purchaseDTOList, HttpServletRequest request){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Address> optionalAddress = addressDAO.findById(addressId);
        if(!optionalAddress.isPresent()) return Result.wrapErrorResult(new AddressNotExistedError());
        if(!userId.equals(optionalAddress.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        BookOrder bookOrder = new BookOrder();
        int total_quantity = 0;
        Double total_price = 0.0d;
        for(PurchaseDTO purchaseDTO : purchaseDTOList){

        }
        bookOrder.setAddress(optionalAddress.get());
        bookOrder.setPostCost(0.0d);
        bookOrder.setStartTime(new Date());
        bookOrder.setStatus("unpaid");
        Set<OrderDetail> orderDetails = new HashSet<>();
        for(PurchaseDTO purchaseDTO : purchaseDTOList){

            OrderDetail orderDetail = new OrderDetail();
            Optional<Book> optionalBook = bookDAO.findById(purchaseDTO.getBookId());
            if(!optionalBook.isPresent()){
                return Result.wrapErrorResult(new BookNotExistedError());
            }
            total_quantity += purchaseDTO.getQuantity();
            Book book = optionalBook.get();
            total_price+=book.getPriceAfterDiscount()*purchaseDTO.getQuantity();
            orderDetail.setBook(book);
            orderDetail.setPrice(book.getPriceAfterDiscount());
            orderDetail.setQuantity(purchaseDTO.getQuantity());
            orderDetail.setBookOrder(bookOrder);
            orderDetails.add(orderDetail);
        }
        bookOrder.setPrice(total_price);
        bookOrder.setQuantity(total_quantity);
        bookOrder.setOrderDetailSet(orderDetails);
        bookOrderDAO.save(bookOrder);
        for(OrderDetail orderDetail: orderDetails){
            orderDetailDAO.save(orderDetail);
        }
        return Result.wrapSuccessfulResult("Saved");
    }

    public  Result<DetailedOrderInformation> getOrderDetail(HttpServletRequest request, String orderId){
        int id = Integer.parseInt(orderId);

        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());

        Optional<BookOrder> optionalBookOrder = bookOrderDAO.findById(id);
        if(!optionalBookOrder.isPresent()) return Result.wrapErrorResult(new BookOrderNotExistedError());
        Iterable<OrderDetail> orderDetails = orderDetailDAO.getAllByBookOrder_Id(id);
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        for(OrderDetail orderDetail : orderDetails){
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setBookId(orderDetail.getBook());
            purchaseDTO.setBookName(orderDetail.bookInstance().getName());
            purchaseDTO.setQuantity(orderDetail.getQuantity());
            purchaseDTOList.add(purchaseDTO);
        }

        DetailedOrderInformation detailedOrderInformation = new DetailedOrderInformation(optionalBookOrder.get(), purchaseDTOList);
        return Result.wrapSuccessfulResult(detailedOrderInformation);
    }

    public Result <List<ViewSingleOrderInformation>> getOrders (HttpServletRequest request) {  //暂时返回所有列表
        String userId = resolveSessionIDInCookie(request);
        if (userId == null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser = userDAO.findById(userId);
        if (!optionalUser.isPresent()) return Result.wrapErrorResult(new UserNotExistedError());

        Set<Address> addressSetInstance = optionalUser.get().addressSetInstance();  //find current user's all address
        List<Address> addressList = new ArrayList<Address>(addressSetInstance);
        List<ViewSingleOrderInformation> viewSingleOrderInformationList = new ArrayList<>();
        for (int i = 0; i < addressList.size(); i++) {
            Set<BookOrder> bookOrderSet = addressList.get(i).BookOrderSetInstance(); //get all book orders per address
            for (BookOrder bookOrder : bookOrderSet) {
                viewSingleOrderInformationList.add(new ViewSingleOrderInformation(bookOrder));
            }
        }
        return Result.wrapSuccessfulResult(viewSingleOrderInformationList);
    }

    public Result<String> editOrder(HttpServletRequest request, EditDTO editDTO){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return  Result.wrapErrorResult(new UserNotExistedError());

        Optional<BookOrder> optionalBookOrder = bookOrderDAO.findById(Integer.parseInt(editDTO.getOrderId()));
        if(!optionalBookOrder.isPresent()) return  Result.wrapErrorResult(new BookOrderNotExistedError());

        //找到Address
        Optional<Address> optionalAddress = addressDAO.findById(optionalBookOrder.get().getAddress());
        if(!optionalAddress.isPresent()) return  Result.wrapErrorResult(new AddressNotExistedError());

        optionalAddress.get().setName(editDTO.getReciever());
        optionalAddress.get().setPhone(editDTO.getPhone());
        optionalAddress.get().setLocation(editDTO.getLocation());
        addressDAO.save(optionalAddress.get());
        return Result.wrapSuccessfulResult("Updated");
    }

    public Result<String> deleteOrder(HttpServletRequest request, String orderId){
        String userId=resolveSessionIDInCookie(request);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<User> optionalUser=userDAO.findById(userId);
        if(!optionalUser.isPresent()) return  Result.wrapErrorResult(new UserNotExistedError());

        //找到该订单
        Optional<BookOrder> optionalBookOrder = bookOrderDAO.findById(Integer.parseInt(orderId));
        if(!optionalBookOrder.isPresent()) return  Result.wrapErrorResult(new BookOrderNotExistedError());

        //delete
        bookOrderDAO.delete(optionalBookOrder.get());
        return Result.wrapSuccessfulResult("Deleted");
    }

}
