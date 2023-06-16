package com.example.demo;

import com.example.demo.errors.server.RequestMissingParameterException;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
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

    public void setRoomsDetails(@RequestBody Map<Object, String> request) throws RequestMissingParameterException {
        if (request.get("hostId") == null) {
            throw new RequestMissingParameterException("hostId");
        }
        if (request.get("topic") == null) {
            throw new RequestMissingParameterException("topic");
        }
        this.setHostId(request.get("hostId"));
        this.setTopic(request.get("topic"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getId(), room.getId()) && Objects.equals(getTopic(), room.getTopic()) && Objects.equals(getHostId(), room.getHostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTopic(), getHostId());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", hostId='" + hostId + '\'' +
                '}';
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


