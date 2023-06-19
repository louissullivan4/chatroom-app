package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Entity
class Message {
    private @Id
    @GeneratedValue Long id;
    private @Column String content;
    private @Column Long accountId;
    private @Column Long roomId;

    Message(String content, Long accountId, Long roomId) {
        this.content = content;
        this.accountId = accountId;
        this.roomId = roomId;
    }

    public Message() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", content='" + getContent() + "'" + ", senderAccountId='"
                + getAccountId() + "'" + ", roomSentToId='" + getRoomId() + "'" + "}";
    }

    public Message setMessageDetails(@RequestBody Map<Object, String> request) throws RequestMissingParameterException {
        if (request.get("content") == null) {
            throw new RequestMissingParameterException("content");
        }
        if (request.get("accountId") == null) {
            throw new RequestMissingParameterException("accountId");
        }
        if (request.get("roomId") == null) {
            throw new RequestMissingParameterException("roomId");
        }
        this.content = request.get("content");
        this.accountId = Long.parseLong(request.get("accountId"));
        this.roomId = Long.parseLong(request.get("roomId"));
        return this;
    }
}

