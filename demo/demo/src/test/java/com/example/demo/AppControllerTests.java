package com.example.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
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
    public void newRoomShouldCreateRoom() throws Exception {
        Room room = new Room("testTopic", "123");
        when(roomRepository.save(room)).thenReturn(room);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\"}")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.hostId").value("123"));
    }

    @Test
    public void updateRoomShouldUpdate() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room("testTopic", "456")));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/rooms/1").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\"}")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.hostId").value("123"));
    }

    @Test
    public void updateRoomShouldErrorIfNoMatch() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/rooms/1").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\"}")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void allAccountsShouldCallRepository() throws Exception {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    public void oneAccountShouldCallRepository() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account("test@gmail.com", "Cian", "McDonald", "user1", new Locale("en", "IE"), LocalDate.of(2001, 5, 16))));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void oneAccountShouldErrorIfNoMatch() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void newAccountShouldCreateNewAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts").contentType("application/json").content("{\"email\":\"amy@gmail.com\", \"firstName\":\"Amy\", \"lastName\":\"Murphy\", \"username\":\"amurphy1\", \"country\":\"en_IE\", \"dob\":\"1980_1_1\"}"
                .getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("amurphy1"));
    }

    @Test
    public void updateAccountShouldChangeSavedAccount() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.of(new Account("bob@gmail.com", "Bob", "Dylan", "bdylan1", new Locale("en", "IE"), LocalDate.of(1960, 1, 1))));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/2").contentType("application/json").content("{\"email\":\"bob@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"dylan1bob\", \"country\":\"en_IE\", \"dob\":\"1960_1_1\"}"
                .getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("dylan1bob"));

    }

    @Test
    public void updateAccountShouldErrorIfNoMatch() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/2").contentType("application/json").content("{\"email\":\"bob@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"dylan1bob\", \"country\":\"en_IE\", \"dob\":\"1960_1_1\"}"
                .getBytes()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteAccountShouldRemoveAccount() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.of(new Account("bob@gmail.com", "Bob", "Dylan", "bdylan1", new Locale("en", "IE"), LocalDate.of(1960, 1, 1))));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/2")).andDo(print()).andExpect(status().isOk());
    }
}
