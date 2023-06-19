package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomId(Long roomId);

    List<Message> findByAccountId(Long accountId);
}
