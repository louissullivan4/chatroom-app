package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

@Entity
class Room {
    private @Id @GeneratedValue Long id;
    private @Column String topic;    
    private @Column String hostId;    

    Room(String topic, String hostId) {
        this.topic = topic;
        this.hostId = hostId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getHostId() {
        return this.hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Room() {}


}


