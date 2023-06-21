package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.example.demo.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
public class AccountTests {

    @Test
    void newAccountDetailsUpdatesValues() throws RequestMissingParameterException {
        Account account = new Account();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode request = objectMapper.createObjectNode();
        request.put("email", "amy@gmail.com");
        request.put("firstName", "Amy");
        request.put("lastName", "Smith");
        request.put("username", "asmith1");
        ObjectNode dob = objectMapper.createObjectNode();
        dob.put("year", 1990);
        dob.put("month", 1);
        dob.put("day", 1);
        request.set("dob", dob);
        ObjectNode location = objectMapper.createObjectNode();
        location.put("latitude", 51.5074);
        location.put("longitude", 0.1278);
        request.set("location", location);
        Account updatedAccount = account.newAccountDetails(request);
        assertEquals("amy@gmail.com", updatedAccount.getEmail());
        assertEquals("Amy", updatedAccount.getFirstName());
        assertEquals("Smith", updatedAccount.getLastName());
        assertEquals("asmith1", updatedAccount.getUsername());
        assertEquals(LocalDate.of(1990, 1, 1), updatedAccount.getDob());
        assertEquals(51.5074, updatedAccount.getLocation().getLatitude());
        assertEquals(0.1278, updatedAccount.getLocation().getLongitude());
    }

    @Test
    void newAccountDetailsThrowsErrorIfIncorrectParameter() throws RequestMissingParameterException {
        Account account = new Account();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode request = objectMapper.createObjectNode();
        request.put("malformed", "123");
        assertThrows(RequestMissingParameterException.class, () -> {account.newAccountDetails(request);});
    }
}