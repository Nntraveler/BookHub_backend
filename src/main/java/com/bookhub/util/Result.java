package com.bookhub.util;



import com.bookhub.error.ServiceError;

import java.io.Serializable;
import java.util.Date;


public class Result<T> implements Serializable {

    private Date date;

    private T data;

    private boolean success;

    private String msg;

    private Integer status;

    public Result() {
    }

    public static <T> Result<T> wrapSuccessfulResult(T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.success = true;
        result.date =new Date();
        result.status =200;
        return result;
    }

    public static <T> Result<T> wrapSuccessfulResult(String message, T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.success = true;
        result.msg = message;
        result.date =new Date();
        result.status =200;
        return result;
    }

    public static <T> Result<T> wrapErrorResult(ServiceError error) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.msg = error.getMessage();
        result.status =error.getCode();
        result.date =new Date();
        return result;
    }

    public static <T> Result<T> wrapErrorResult(ServiceError error, Object... extendMsg) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.msg = String.format(error.getMessage(), extendMsg);
        result.status =error.getCode();
        result.date =new Date();
        return result;
    }

    public static <T> Result<T> wrapErrorResult(String message) {
        Result<T> result = new Result<T>();
        result.success = false;
        result.msg = message;
        result.status =-1;
        result.date =new Date();
        return result;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}