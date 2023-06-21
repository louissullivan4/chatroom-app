package com.example.demo;

import com.example.demo.model.Location;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RoomControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void getAllRooms_DBEmpty_StatusNoContent() throws Exception {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void getAllRooms_DBHasValues_ReturnsValues() throws Exception {
        Room room1 = new Room("Peanut Butter","1",new Location(53.3331, -6.2489));
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        when(roomRepository.findAll()).thenReturn(rooms);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].hostId").value("1"));
    }

    @Test
    public void getRoomById_RoomWithIdPresent_ReturnsValues() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room("1", "1", new Location(53.3331, -6.2489))));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.topic").value("1"));
    }

    @Test
    public void getRoomById_NoRoomWithThatId_ThrowsClientError() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void createRoom_RequestIsCorrect_IsCreated() throws Exception {
        Room room = new Room("testTopic", "123", new Location(53.3331, -6.2489));
        when(roomRepository.save(room)).thenReturn(room);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\", \"location\":{\"latitude\":\"53.3331\", \"longitude\":\"-6.2489\" }}")).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.hostId").value("123"));
    }

    @Test
    public void updateRoom_RoomWithIdIsPresent_RoomUpdated() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room("testTopic", "456", new Location(53.3331, -6.2489))));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/rooms/1").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\", \"location\":{\"latitude\":\"53.3331\", \"longitude\":\"-6.2489\" }}")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.hostId").value("123"));
    }

    @Test
    public void updateRoom_RoomWithIdIsNotPresent_ThrowsClientError() throws Exception {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/rooms/1").contentType("application/json").content("{\"hostId\":\"123\", \"topic\":\"testTopic\", \"location\":{\"latitude\":\"53.3331\", \"longitude\":\"-6.2489\" }}")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void createMessageShouldCreateMessage() throws Exception {
        Location location = new Location(53.3331, -6.2489);
        Room room = new Room("testTopic", "123", location);
        when(roomRepository.save(room)).thenReturn(room);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                .contentType("application/json")
                .content("{\"content\":\"I love cheese\", \"accountId\":\"1\", \"roomId\":\"1\"}"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("I love cheese"));
    }

    @Test
    public void allRoomMessagesShouldReturnAllMessages() throws Exception {
        Location location = new Location(53.3331, -6.2489);
        Room room = new Room("testTopic", "123", location);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(messageRepository.findByRoomId(1L)).thenReturn(new ArrayList<>(Arrays.asList(new Message("I love cheese", 1L, 1L), new Message("I love cheese", 1L, 1L))));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1/messages")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}
