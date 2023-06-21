package com.example.demo.model;

import com.example.demo.errors.server.RequestMissingParameterException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Account {
    private @Id @GeneratedValue Long id;
    private @Column String email;
    private @Column String firstName;
    private @Column String lastName;
    private @Column String username;
    private @Column LocalDate dob;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "accounts")
    @JsonIgnore
    private Set<Room> rooms = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public Account(String email, String firstName, String lastName, String username, LocalDate dob, Location location) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.dob = dob;
        this.location = location;
    }

    public Account() {}

    // Getter and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{" + "id=" + this.id + ", email='" + this.email + '\'' + ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' + ", username='" + this.username + '\'' + ", dob='"
                + this.dob + '\'' + ", location='" + this.location + '\'' + '}';
    }

    // Create new account or update details by parsing json values to strings and locale and localdate types
    public Account newAccountDetails(@RequestBody Map<Object, String> request) throws RequestMissingParameterException {
        if (request.get("email") == null) {
            throw new RequestMissingParameterException("email");
        }
        if (request.get("firstName") == null) {
            throw new RequestMissingParameterException("firstName");
        }
        if (request.get("lastName") == null) {
            throw new RequestMissingParameterException("lastName");
        }
        if (request.get("username") == null) {
            throw new RequestMissingParameterException("username");
        }
        if (request.get("dob") == null) {
            throw new RequestMissingParameterException("dob");
        }
        if (request.get("location") == null) {
            throw new RequestMissingParameterException("location");
        }
        this.setEmail(request.get("email"));
        this.setFirstName(request.get("firstName"));
        this.setLastName(request.get("lastName"));
        this.setUsername(request.get("username"));
        this.setDob(LocalDate.of(Integer.parseInt(request.get("dob").split("_")[0]),
                Integer.parseInt(request.get("dob").split("_")[1]),
                Integer.parseInt(request.get("dob").split("_")[2])));
        this.setLocation(new Location(request.get("location").split(" ")[0],
                Double.parseDouble(request.get("location").split(" ")[1]),
                Double.parseDouble(request.get("location").split(" ")[2])));
        return this;
    }
}