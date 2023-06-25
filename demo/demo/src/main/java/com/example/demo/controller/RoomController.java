package com.example.demo.controller;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.example.demo.errors.server.RoomNotFoundException;
import com.example.demo.model.Message;
import com.example.demo.model.Room;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.RoomRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    private final RoomRepository roomRepository;
    RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = new ArrayList<Room>();
        roomRepository.findAll().forEach(rooms::add);

        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/rooms/{id}")
    ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    ResponseEntity<Room> createRoom(@RequestBody Room roomRequest) {
        return new ResponseEntity<>(roomRepository.save(roomRequest), HttpStatus.CREATED);
    }

    @PutMapping("/rooms/{id}")
    ResponseEntity<Room> updateRoom(@RequestBody Room roomRequest, @PathVariable Long id) throws RoomNotFoundException {
        Room _room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
        _room.setHostId(roomRequest.getHostId());
        _room.setTopic(roomRequest.getTopic());
        _room.setLocation(roomRequest.getLocation());
        roomRepository.save(_room);
        return new ResponseEntity<>(_room, HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{id}")
    ResponseEntity<HttpStatus> deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
