package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Turnstile extends User {

    Turnstile(String username, String password){
        super(username, password);
        roles = "TURNSTILE";
    }
}
