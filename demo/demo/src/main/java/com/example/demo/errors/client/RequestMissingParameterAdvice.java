package com.example.demo.errors.client;

import com.example.demo.errors.server.RequestMissingParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RequestMissingParameterAdvice {
    @ResponseBody
    @ExceptionHandler(RequestMissingParameterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String requestMissingParameterHandler(RequestMissingParameterException ex) {
        return "Error " + HttpStatus.NOT_FOUND + ": " + ex.getMessage();
    }
}
