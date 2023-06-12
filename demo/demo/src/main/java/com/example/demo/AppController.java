package com.example.demo;

import java.util.List;

import com.example.demo.errors.server.AccountNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
class AppController {
    private final AccountRepository repository;
    AppController(AccountRepository repository) {
        this.repository = repository;
    }

    // Get all accounts
    @GetMapping("/accounts")
    List<Account> all() {
        return repository.findAll();
    }

    // Get one account by ID
    @GetMapping("/accounts/{id}")
    Account one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}