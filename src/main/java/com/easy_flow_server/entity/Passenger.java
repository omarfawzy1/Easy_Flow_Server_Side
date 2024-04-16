package com.easy_flow_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {
    @OneToOne(optional = false)
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
    @Cascade(CascadeType.ALL)
    private Wallet wallet;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
    @ManyToMany(cascade = {jakarta.persistence.CascadeType.PERSIST, jakarta.persistence.CascadeType.MERGE})
    @JoinTable(name = "passenger_privilege", joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges = new HashSet<>();
    private String city;
    @Column(nullable = false)
    private Gender gender;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "pin")
    private String pin;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "birth_day", nullable = false)
    private java.util.Date birthDay;
    @OneToMany(mappedBy = "passenger")
    private Set<Trip> trips = new HashSet<>();
    @OneToMany(mappedBy = "passenger", cascade = jakarta.persistence.CascadeType.ALL)
    private Set<Subscription> subscriptions = new HashSet<>();

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
        this.email = email;
        this.pin="0000";
    }

    public Passenger(String firstName, String lastName, String phoneNumber, String city, Gender gender, Date birthDay, String username, String password, String email) {
        super(username, password);
        roles = "PASSENGER";
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email = email;
        this.pin="0000";

    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return this.username.equals(passenger.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
