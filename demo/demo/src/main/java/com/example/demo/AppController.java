package com.example.demo;

import java.util.List;
import java.util.Map;

import com.example.demo.errors.server.AccountNotFoundException;
import com.example.demo.errors.server.RoomNotFoundException;

import org.springframework.web.bind.annotation.*;

@RestController
class AppController {
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;
    AppController(AccountRepository accountRepository, RoomRepository roomRepository) {
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
    }

    // Get all accounts
    @GetMapping("/accounts")
    List<Account> allAccounts() {
        return accountRepository.findAll();
    }

    // Get one account by ID
    @GetMapping("/accounts/{id}")
    Account oneAccount(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    // Create a new account
    @PostMapping("/accounts")
    Account newAccount(@RequestBody Map<Object, String> request) {
        Account account = new Account();
        account.newAccountDetails(request);
        accountRepository.save(account);
        return account;
    }

    // Update an account
    @PutMapping("/accounts/{id}")
    Account updateAccount(@RequestBody Map<Object, String> request, @PathVariable Long id) throws AccountNotFoundException {
        if (accountRepository.findById(id).isEmpty()) {
            throw new AccountNotFoundException(id);
        } else {
            Account account = accountRepository.findById(id).get();
            account.newAccountDetails(request);
            accountRepository.save(account);
            return account;
        }
    }
    // Delete an account
    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
    }

    // Get all rooms
    @GetMapping("/rooms")
    List<Room> allRooms() {
        return roomRepository.findAll();
    }

    // Get one room by ID
    @GetMapping("/rooms/{id}")
    Room oneRoom(@PathVariable Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    @PostMapping("/rooms")
    Room newRoom(@RequestBody Map<Object, String> request) {
        Room room = new Room();
        room.setRoomsDetails(request);
        roomRepository.save(room);
        return room;
    }

    @PutMapping("/rooms/{id}")
    Room updateRoom(@RequestBody Map<Object, String> request, @PathVariable Long id) throws RoomNotFoundException {
        if (roomRepository.findById(id).isEmpty()) {
            throw new RoomNotFoundException(id);
        } else {
            Room room = roomRepository.findById(id).get();
            room.setRoomsDetails(request);
            roomRepository.save(room);
            return room;
        }
    }

    @DeleteMapping("/rooms/{id}")
    void deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
    }

}