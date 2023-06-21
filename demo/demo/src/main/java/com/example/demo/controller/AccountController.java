package com.example.demo.controller;

import com.example.demo.errors.server.AccountNotFoundException;
import com.example.demo.errors.server.RoomNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Room;
import com.example.demo.model.Message;
import com.example.demo.model.RecommendationService;
import com.example.demo.model.Room;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/accounts")
    ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = new ArrayList<>(accountRepository.findAll());

        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }

    @GetMapping("/rooms/{id}/accounts")
    ResponseEntity<List<Account>> getAllAccountsByRoomId(@PathVariable Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException(id);
        }

        List<Account> accounts = accountRepository.findAccountsByRoomsId(id);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}/rooms")
        ResponseEntity<List<Room>> getAllRoomsByAccountId(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException(id);
        }
        List<Room> rooms = roomRepository.findRoomsByAccountsId(id);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/rooms/{id}/accounts")
    ResponseEntity<Account> addAccountToRoom(@PathVariable Long id, @RequestBody Account accountRequest) {
        Account account = roomRepository.findById(id).map(room -> {
           long accountId = accountRequest.getId();
           Account _account = accountRepository.findById(accountId)
                   .orElseThrow(() -> new AccountNotFoundException(id));
           room.addAccount(_account);
           roomRepository.save(room);
           return _account;
        }).orElseThrow(() -> new RoomNotFoundException(id));
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PostMapping("/accounts")
    ResponseEntity<Account> addNewAccount(@RequestBody Map<Object, String> request) {
        Account account = new Account();
        account.newAccountDetails(request);
        accountRepository.save(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{id}")
    ResponseEntity<Account> updateAccount(@RequestBody Map<Object, String> request, @PathVariable Long id) throws AccountNotFoundException {
        if (accountRepository.findById(id).isEmpty()) {
            throw new AccountNotFoundException(id);
        } else {
            Account account = accountRepository.findById(id).get();
            account.newAccountDetails(request);
            accountRepository.save(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @DeleteMapping("/accounts/{id}")
    ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
