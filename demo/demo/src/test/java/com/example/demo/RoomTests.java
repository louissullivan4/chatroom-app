package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.example.demo.model.Account;
import com.example.demo.model.Location;
import com.example.demo.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoomTests {

    @Test
    void setRoomsDetailsUpdatesValues() throws RequestMissingParameterException {
        Room room = new Room();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode request = objectMapper.createObjectNode();
        request.put("hostId", "123");
        request.put("topic", "testTopic");
        ObjectNode location = objectMapper.createObjectNode();
        location.put("latitude", 51.5074);
        location.put("longitude", 0.1278);
        request.set("location", location);
        Room updatedRoom = room.setRoomsDetails(request);
        assertEquals("123", room.getHostId());
        assertEquals("testTopic", room.getTopic());
        assertEquals(51.5074, updatedRoom.getLocation().getLatitude());
        assertEquals(0.1278, updatedRoom.getLocation().getLongitude());
    }

   @Test
    void setRoomsDetailsThrowsErrorIfIncorrectParameter() throws RequestMissingParameterException {
       Room room = new Room();
       ObjectMapper objectMapper = new ObjectMapper();
       ObjectNode request = objectMapper.createObjectNode();
       request.put("malformed", "123");
       assertThrows(RequestMissingParameterException.class, () -> {room.setRoomsDetails(request);});
   }
}
