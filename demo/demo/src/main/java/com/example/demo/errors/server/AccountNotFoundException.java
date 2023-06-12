package com.example.demo.errors.server;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Could not find account with the id '" + id + "'");

    }
}
