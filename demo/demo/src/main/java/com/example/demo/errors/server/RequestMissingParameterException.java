package com.example.demo.errors.server;

public class RequestMissingParameterException extends RuntimeException {
    public RequestMissingParameterException(String parameter) {
        super("Request is missing " + parameter);
    }
}
