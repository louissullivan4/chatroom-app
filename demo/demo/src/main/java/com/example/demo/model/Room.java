package com.example.demo.model;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Set;

@Entity
public class Room {
    private @Id @GeneratedValue Long id;
    private @Column String topic;    
    private @Column String hostId;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "room_registrations", joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")})
    private Set<Account> accounts = new HashSet<>();

    public Room(String topic, String hostId, Location location) {
        this.topic = topic;
        this.hostId = hostId;
        this.location = location;
    }

    public Room setRoomsDetails(@RequestBody JsonNode request) throws RequestMissingParameterException {
        if (request.get("hostId") == null) {
            throw new RequestMissingParameterException("hostId");
        }
        if (request.get("topic") == null) {
            throw new RequestMissingParameterException("topic");
        }
        this.setHostId(request.get("hostId").asText());
        this.setTopic(request.get("topic").asText());
        JsonNode location = request.get("location");
        this.setLocation(new Location(location.get("latitude").asDouble(), location.get("longitude").asDouble()));
        return this;
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
                ", location='" + location + '\'' +
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.getRooms().add(this);
    }

    public void removeAccount(Long accountId) {
        Account account = this.accounts.stream().filter(a -> a.getId() == accountId).findFirst().orElse(null);
        if (account != null) {
            this.accounts.remove(account);
            account.getRooms().remove(this);
        }
    }
    public Room() {}
}


