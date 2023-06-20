package com.example.demo.controller;

import com.example.demo.errors.server.RoomNotFoundException;
import com.example.demo.model.Message;
import com.example.demo.model.Room;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;
    private final MessageRepository msgRepository;

    RoomController(RoomRepository roomRepository, MessageRepository msgRepository) {
        this.roomRepository = roomRepository;
        this.msgRepository = msgRepository;
    }

    // Get all rooms
    @GetMapping("/rooms")
    List<Room> allRooms() {
        return roomRepository.findAll();
    }

    // Get one room by ID
    @GetMapping("/rooms/{id}")
    Room oneRoom(@PathVariable Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    @PostMapping("/rooms")
    Room newRoom(@RequestBody Map<Object, String> request) {
        Room room = new Room();
        room.setRoomsDetails(request);
        roomRepository.save(room);
        return room;
    }

    @PutMapping("/rooms/{id}")
    Room updateRoom(@RequestBody Map<Object, String> request, @PathVariable Long id) throws RoomNotFoundException {
        if (roomRepository.findById(id).isEmpty()) {
            throw new RoomNotFoundException(id);
        } else {
            Room room = roomRepository.findById(id).get();
            room.setRoomsDetails(request);
            roomRepository.save(room);
            return room;
        }
    }

    @DeleteMapping("/rooms/{id}")
    void deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
    }

    // Get all messages in a room
    @GetMapping("/rooms/{id}/messages")
    List<Message> allRoomMessages(@PathVariable Long id) {
        return msgRepository.findByRoomId(id);
    }

    // New Message Sent
    @PostMapping("/messages")
    Message createMessage(@RequestBody Map<Object, String> request) {
        Message message = new Message();
        message.setMessageDetails(request);
        msgRepository.save(message);
        return message;
    }
}
