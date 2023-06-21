package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.Location;
import com.example.demo.model.Message;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MessageRepository;
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
    CommandLineRunner initDatabase(AccountRepository accountRepository, RoomRepository roomRepository, MessageRepository msgRepository) {
        return args -> {
            Location locA = new Location("Cork_IE", 51.8969, -8.4863);
            Location locB = new Location("Istanbul_TR", 41.0082, 28.9784);
            log.info("Preloading " + accountRepository.save(new Account("louis@gmail.com", "Louis", "Sullvian", "lsullivan1", LocalDate.of(2001, 5, 16), locA)));
            log.info("Preloading " + accountRepository.save(new Account("bob@gmail.com", "Bob", "Dylan", "bdylan1", LocalDate.of(1960, 1, 1), locB)));

            Location loc1 = new Location("Berlin_DE", 52.5200, 13.4050);
            Location loc3 = new Location("Galway_IE", 53.2707, -9.0568);
            log.info("Preloading " + roomRepository.save(new Room("Peanut Butter","1", loc1)));
            log.info("Preloading " + roomRepository.save(new Room("Jelly","2", loc3)));

            log.info("Preloading " + msgRepository.save(new Message("Hey Friend!", 1L, 1L)));
            log.info("Preloading " + msgRepository.save(new Message("This rocks!", 2L, 2L)));
        };
    }
}
