package com.example.demo;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void allRoomsShouldCallRepository() throws Exception {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    public void oneRoomShouldCallRepository() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room("1", "1")));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.topic").value("1"));
    }

    @Test
    public void oneRoomShouldErrorIfNoMatch() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void allAccountsShouldCallRepository() throws Exception {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    public void oneAccountShouldCallRepository() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account("test@gmail.com", "Cian", "McDonald", "user1" , new Locale("en", "IE"), LocalDate.of(2001, 5, 16))));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void oneAccountShouldErrorIfNoMatch() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().is4xxClientError());
    }
}
