package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

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
    // curl localhost:8080/accounts
    @GetMapping("/accounts")
    List<Account> allAccounts() {
        return accountRepository.findAll();
    }

    // Get one account by ID
    // curl localhost:8080/accounts/1
    // curl localhost:8080/accounts/2
    @GetMapping("/accounts/{id}")
    Account oneAccount(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    // Create a new account
    // curl -H "Content-Type: application/json" -X POST http://localhost:8080/accounts -d "{\"email\":\"amy@gmail.com\", \"firstName\":\"Amy\", \"lastName\":\"Murphy\", \"username\":\"amurphy1\", \"country\":\"en_IE\", \"dob\":\"1980_1_1\"}"
    @PostMapping("/accounts")
    Account newAccount(@RequestBody Map<Object, String> request) {
        Account account = new Account();
        account.newAccountDetails(request);
        accountRepository.save(account);
        return account;
    }

    // Update an account
    // curl -H "Content-Type: application/json" -X PUT http://localhost:8080/accounts/2 -d "{\"email\":\"dylan@gmail.com\", \"firstName\":\"Bob\", \"lastName\":\"Dylan\", \"username\":\"bdylan1\", \"country\":\"en_IE\", \"dob\":\"1960_1_1\"}"
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
    // curl -X DELETE http://localhost:8080/accounts/4
    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);

    }

    // Get all accounts
    @GetMapping("/rooms")
    List<Room> allRooms() {
        return roomRepository.findAll();
    }

    // Get one account by ID
    @GetMapping("/rooms/{id}")
    Room oneRoom(@PathVariable Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }
}