package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.Instant;
import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {
    @OneToOne(optional = false)
    @JoinColumn(name = "wallet_id", referencedColumnName ="wallet_id")
    @Cascade(CascadeType.ALL)
    private Wallet wallet;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number",unique = true ,nullable = false)
    private String phoneNumber;
    private String type;
    private String city;
    @Column(nullable = false)
    private Gender gender;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "pin")
    private int pin;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Column(name = "birth_day",nullable = false)
    private java.util.Date birthDay;
    @OneToMany(mappedBy = "passenger", cascade = jakarta.persistence.CascadeType.ALL)
    private Set<Trip> trips= new HashSet<>();

    public Passenger(Wallet wallet, String firstName, String lastName, String phoneNumber, String type, String city, Gender gender, Date birthDay, String username, String password, String email) {
        super(username, password);
        roles = "PASSENGER";
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email=email;
    }
    public Passenger( String firstName, String lastName, String phoneNumber, String type, String city, Gender gender, Date birthDay, String username, String password, String email) {
        super(username, password);
        roles = "PASSENGER";
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email=email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return wallet.equals(passenger.wallet) && this.username.equals(passenger.username) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
