package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.Room;
import com.example.demo.model.Location;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    public void getAllRooms_DBEmpty_StatusNoContent() throws Exception {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts")).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void getAllRooms_DBHasValues_ReturnsValues() throws Exception {
        Account account = new Account("louis@gmail.com", "Louis", "Sullvian", "lsullivan1", LocalDate.of(2001, 5, 16), new Location("Dublin_IE", 53.3331, -6.2489));
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountRepository.findAll()).thenReturn(accounts);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName").value("Louis"));
    }

    @Test
    public void getAllAccountsByRoomId_NoRoomWithThatId_ThrowsClientError() throws Exception {
        when(roomRepository.existsById(1L)).thenReturn(false);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1/accounts")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllAccountsByRoomId_RoomWithThatIdPresent_ReturnsAccounts() throws Exception {
        when(roomRepository.existsById(1L)).thenReturn(true);
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account("louis@gmail.com", "Louis", "Sullvian", "lsullivan1", LocalDate.of(2001, 5, 16), new Location("Dublin_IE", 53.3331, -6.2489)));
        when(accountRepository.findAccountsByRoomsId(1L)).thenReturn(accounts);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1/accounts")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName").value("Louis"));
    }

    @Test
    public void getAccountById_AccountWithThatIdIsPresent_ReturnsAccount() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account("test@gmail.com", "Cian", "McDonald", "user1", LocalDate.of(2001, 5, 16), new Location("Dublin_IE", 53.3331, -6.2489))));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void getAccountById_AccountWithThatIdIsNotPresent_ThrowsClientError() throws Exception {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllRoomsByAccountId_AccountWithThatIdIsPresent_ReturnsRooms() throws Exception {
        when(accountRepository.existsById(1L)).thenReturn(true);
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Peanut Butter", "1", new Location("Dublin_IE", 53.3331, -6.2489)));
        when(roomRepository.findRoomsByAccountsId(1L)).thenReturn(rooms);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1/rooms")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].hostId").value("1"));
    }

    @Test
    public void getAllRoomsByAccountId_AccountWithThatIdIsNotPresent_ThrowsClientError() throws Exception {
        when(accountRepository.existsById(1L)).thenReturn(false);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1/rooms")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void addAccountToRoom_AccountAndRoomExist_AccountAdded() throws Exception {
        Account account = new Account("louis@gmail.com", "Louis", "Sullvian", "lsullivan1", LocalDate.of(2001, 5, 16),new Location("Dublin_IE", 53.3331, -6.2489));
        account.setId(1L);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room("Peanut Butter", "1", new Location("Dublin_IE", 53.3331, -6.2489))));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms/1/accounts")
                .content(TestUtil.asJsonString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("lsullivan1"));;
    }

    @Test
    public void addNewAccount_CreatesAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts").contentType("application/json").content("{\"email\":\"amy@gmail.com\", \"firstName\":\"Amy\", \"lastName\":\"Murphy\", \"username\":\"amurphy1\", \"dob\":\"1980_1_1\", \"location\":\"Dublin_IE 53.339428 -6.257664\"}"
                        .getBytes()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("amurphy1"));
    }

    @Test
    public void updateAccount_AccountWithIdIsPresent_UpdatesAccount() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.of(new Account("bob@gmail.com", "Bob", "Dylan", "bdylan1", LocalDate.of(1960, 1, 1), new Location("Dublin_IE", 53.3331, -6.2489))));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/2").contentType("application/json").content("{\"email\":\"bob@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"dylan1bob\", \"dob\":\"1960_1_1\", \"location\":\"Dublin_IE 53.339428 -6.257664\"}"
                        .getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("dylan1bob"));

    }
    @Test
    public void updateAccount_AccountWithIdIsNotPresent_ThrowsClientError() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/2").contentType("application/json").content("{\"email\":\"bob@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"dylan1bob\", \"dob\":\"1960_1_1\", \"location\":\"Dublin_IE 53.339428 -6.257664\"}".getBytes())).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteAccount_AccountWithIdIsPresent_AccountDeleted() throws Exception {
        when(accountRepository.findById(2L)).thenReturn(Optional.of(new Account("bob@gmail.com", "Bob", "Dylan", "bdylan1", LocalDate.of(1960, 1, 1),new Location("Dublin_IE", 53.3331, -6.2489))));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/2")).andDo(print()).andExpect(status().isNoContent());
    }
}
