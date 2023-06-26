package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ManyToMany(cascade = {jakarta.persistence.CascadeType.PERSIST,jakarta.persistence.CascadeType.MERGE})
    @JoinTable(name="passenger_privilege", joinColumns=@JoinColumn(name="passenger_id"),
            inverseJoinColumns=@JoinColumn(name="privilege_id"))
    private Set<Privilege> privileges= new HashSet<>();
    private String city;
    @Column(nullable = false)
    private Gender gender;
    @Column(name = "email",nullable = false, unique = true)
    private String email;
    @Column(name = "pin")
    private int pin;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Column(name = "birth_day",nullable = false)
    private java.util.Date birthDay;
    @OneToMany(mappedBy = "passenger")
    private Set<Trip> trips= new HashSet<>();
    @OneToMany(mappedBy = "passenger", cascade = jakarta.persistence.CascadeType.ALL)
    private Set<Subscription> subscriptions= new HashSet<>();

    public Passenger(Wallet wallet, String firstName, String lastName, String phoneNumber, String city, Gender gender, Date birthDay, String username, String password, String email) {
        super(username, password);
        roles = "PASSENGER";
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email=email;
    }
    public Passenger( String firstName, String lastName, String phoneNumber, String city, Gender gender, Date birthDay, String username, String password, String email) {
        super(username, password);
        roles = "PASSENGER";
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email=email;
    }
    public void addPrivlage(Privilege privilege){
        this.privileges.add(privilege);
    }
    public void removePrivlage(Privilege privilege){
        this.privileges.remove(privilege);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return this.username.equals(passenger.username) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
