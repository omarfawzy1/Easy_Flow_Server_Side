package com.example.easy_flow_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends User {

    @Column(nullable = false)
    private String name;

    public Administrator(String id, String name, String username, String password) {
        super(username, password);
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        roles = "ADMIN";
    }

}
