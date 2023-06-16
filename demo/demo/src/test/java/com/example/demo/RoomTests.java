package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
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
        Map<Object, String> request = new HashMap<>();
        request.put("hostId", "123");
        request.put("topic", "testTopic");
        room.setRoomsDetails(request);
        assertEquals("123", room.getHostId());
        assertEquals("testTopic", room.getTopic());
    }

   @Test
    void setRoomsDetailsThrowsErrorIfIncorrectParameter() throws RequestMissingParameterException {
       Room room = new Room();
       Map<Object, String> request = new HashMap<>();
       request.put("malformed", "123");
       assertThrows(RequestMissingParameterException.class, () -> {room.setRoomsDetails(request);});
   }
}
