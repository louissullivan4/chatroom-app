package com.example.demo;

import java.util.List;

import com.example.demo.errors.server.AccountNotFoundException;
import com.example.demo.errors.server.RoomNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/rooms")
    List<Room> allRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    Room oneRoom(@PathVariable Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }
}