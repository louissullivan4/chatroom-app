package com.example.demo.errors.server;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("Could not find room with the id '" + id + "'");

    }
}
