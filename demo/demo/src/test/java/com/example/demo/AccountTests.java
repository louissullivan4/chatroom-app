package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.example.demo.model.Account;
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
        Map<Object, String> request = new HashMap<>();
        request.put("email", "tim@gmail.com");
        request.put("firstName", "Tim");
        request.put("lastName", "Smith");
        request.put("username", "timmy1");
        request.put("dob", "1990_01_01");
        request.put("location", "Dublin_IE 53.3331 -6.2489");
        account.newAccountDetails(request);
        assertEquals("tim@gmail.com", account.getEmail());
        assertEquals("Tim", account.getFirstName());
        assertEquals("Smith", account.getLastName());
        assertEquals("timmy1", account.getUsername());
        assertEquals(LocalDate.of(1990, 1, 1),  account.getDob());
    }

    @Test
    void newAccountDetailsThrowsErrorIfIncorrectParameter() throws RequestMissingParameterException {
        Account account = new Account();
        Map<Object, String> request = new HashMap<>();
        request.put("malformed", "tim@gmail.com");
        assertThrows(RequestMissingParameterException.class, () -> {account.newAccountDetails(request);});
    }
}