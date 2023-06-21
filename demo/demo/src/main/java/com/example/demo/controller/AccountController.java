package com.example.demo.controller;

import com.example.demo.errors.server.AccountNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Message;
import com.example.demo.model.RecommendationService;
import com.example.demo.model.Room;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    private final MessageRepository msgRepository;
    private final RoomRepository roomRepository;

    AccountController(AccountRepository accountRepository, RoomRepository roomRepository, MessageRepository msgRepository) {
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
        this.msgRepository = msgRepository;

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

    // Get all messages sent by a user
    @GetMapping("/accounts/{id}/messages")
    List<Message> allAccountMessages(@PathVariable Long id) {
        return msgRepository.findByAccountId(id);
    }

    // Recommend rooms to a user
    @GetMapping("/accounts/{id}/recommendations")
    List<Room> recommendRooms(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        RecommendationService recommendationService = new RecommendationService();
        return recommendationService.recommendRooms(account, roomRepository);
    }

}
