package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.example.demo.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageTests {

    @Test
    void setMessageDetailsUpdatesValues() throws RequestMissingParameterException {
        Message message = new Message();
        Map<Object, String> request = new HashMap<>();
        request.put("content", "I love cheese");
        request.put("accountId", "1");
        request.put("roomId", "1");
        message.setMessageDetails(request);
        assertEquals("I love cheese", message.getContent());
        assertEquals(1L, message.getAccountId());
        assertEquals(1L, message.getRoomId());
    }

    @Test
    void setMessageDetailsThrowsErrorIfIncorrectParameter() throws RequestMissingParameterException {
        Message message = new Message();
        Map<Object, String> request = new HashMap<>();
        request.put("malformed", "123");
        assertThrows(RequestMissingParameterException.class, () -> {message.setMessageDetails(request);});
    }
}
