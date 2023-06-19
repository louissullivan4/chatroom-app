package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
public class Account {
    private @Id @GeneratedValue Long id;
    private @Column String email;
    private @Column String firstName;
    private @Column String lastName;
    private @Column String username;
    private @Column Locale country;
    private @Column LocalDate dob;

    public Account(String email, String firstName, String lastName, String username, Locale country, LocalDate dob) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.country = country;
        this.dob = dob;
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

    public Locale getCountry() {
        return country;
    }

    public void setCountry(Locale country) {
        this.country = country;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    // Methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Account account))
            return false;
        return Objects.equals(this.id, account.id) && Objects.equals(this.email, account.email)
                && Objects.equals(this.firstName, account.firstName)
                && Objects.equals(this.lastName, account.lastName)
                && Objects.equals(this.username, account.username)
                && Objects.equals(this.country, account.country)
                && Objects.equals(this.dob, account.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.email, this.firstName, this.lastName, this.username, this.country, this.dob);
    }

    @Override
    public String toString() {
        return "{" + "uid=" + this.id + ", email='" + this.email + '\'' + ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' + ", username='" + this.username + '\'' + ", country='" +
                this.country + '\'' + ", dob='" + this.dob + '\'' + '}';
    }

    // Create new account or update details by parsing json values to strings and locale and localdate types
    public Account newAccountDetails(@RequestBody Map<Object, String> request){
        this.setEmail(request.get("email"));
        this.setFirstName(request.get("firstName"));
        this.setLastName(request.get("lastName"));
        this.setUsername(request.get("username"));
        this.setCountry(new Locale(request.get("country").split("_")[0], request.get("country").split("_")[1]));
        this.setDob(LocalDate.of(Integer.parseInt(request.get("dob").split("_")[0]),
                Integer.parseInt(request.get("dob").split("_")[1]),
                Integer.parseInt(request.get("dob").split("_")[2])));
        return this;
    }
}