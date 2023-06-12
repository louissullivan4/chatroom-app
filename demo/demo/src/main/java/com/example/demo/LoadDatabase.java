package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, RoomRepository roomRepository) {
        return args -> {
            log.info("Preloading " + accountRepository.save(new Account("louis@gmail.com", "Louis", "Sullvian", "lsullivan1", new Locale("en", "IE"), LocalDate.of(2001, 5, 16))));
            log.info("Preloading " + roomRepository.save(new Room("Peanut Butter","1")));

        };
    }
}
