package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Turnstile extends User {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    Turnstile(String username, String password, Owner owner){
        super(username, password);
        this.owner = owner;
        roles = "TURNSTILE";
    }
}
