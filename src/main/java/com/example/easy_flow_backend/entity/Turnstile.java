package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Turnstile extends User {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @OneToMany(mappedBy = "startTurnstile", cascade = CascadeType.ALL)
    private Set<Trip> startTrips= new HashSet<>();
    @OneToMany(mappedBy = "endTurnstile", cascade = CascadeType.ALL)
    private Set<Trip> endTrips= new HashSet<>();

    Turnstile(String username, String password, Owner owner){
        super(username, password);
        this.owner = owner;
        roles = "TURNSTILE";
    }
}
