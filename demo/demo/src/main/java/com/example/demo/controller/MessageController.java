package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
public class MessageController {
    private final MessageRepository msgRepository;

    public MessageController(MessageRepository msgRepository) {
        this.msgRepository = msgRepository;
    }

    // Get all messages sent by a user
    @GetMapping("/accounts/{id}/messages")
    List<Message> allAccountMessages(@PathVariable Long id) {
        return msgRepository.findByAccountId(id);
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
