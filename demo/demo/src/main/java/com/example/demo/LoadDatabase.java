package com.example.demo;

import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, RoomRepository roomRepository) {
        return args -> {
            for(int i=0; i < 35; i++){
                log.info("Preloading " + accountRepository.save(GenerateRandomData.generateRandomAccount()));
            }
            for(int i=0; i < 15; i++){
                log.info("Preloading " + roomRepository.save(GenerateRandomData.generateRandomRoom()));
            }
        };
    }
}
