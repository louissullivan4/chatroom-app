package com.example.demo.controller;

import com.example.demo.errors.server.AccountNotFoundException;
import com.example.demo.errors.server.RoomNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Room;
import com.example.demo.service.RecommendationService;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;

    AccountController(AccountRepository accountRepository, RoomRepository roomRepository) {
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
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

    @PostMapping("/rooms/{id}/accounts/{accountId}")
    ResponseEntity<Account> addAccountToRoom(@PathVariable Long id, @PathVariable Long accountId) {
        Account account = roomRepository.findById(id).map(room -> {
           Account _account = accountRepository.findById(accountId)
                   .orElseThrow(() -> new AccountNotFoundException(id));
           room.addAccount(_account);
           roomRepository.save(room);
           return _account;
        }).orElseThrow(() -> new RoomNotFoundException(id));
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PostMapping("/accounts")
    ResponseEntity<Account> addNewAccount(@RequestBody Account accountRequest) {
        return new ResponseEntity<>(accountRepository.save(accountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{id}")
    ResponseEntity<Account> updateAccount(@RequestBody Account accountRequest, @PathVariable Long id) throws AccountNotFoundException {
        Account _account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        _account.setDob(accountRequest.getDob());
        _account.setEmail(accountRequest.getEmail());
        _account.setLocation(accountRequest.getLocation());
        _account.setRooms(accountRequest.getRooms());
        _account.setUsername(accountRequest.getUsername());
        _account.setFirstName(accountRequest.getFirstName());
        _account.setLastName(accountRequest.getLastName());
        accountRepository.save(_account);
        return new ResponseEntity<>(_account, HttpStatus.OK);

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
