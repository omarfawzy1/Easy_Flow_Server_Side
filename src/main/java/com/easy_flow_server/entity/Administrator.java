package com.easy_flow_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends User {

    @Column(nullable = false)
    private String name;

    public Administrator(String name, String username, String password) {
        super(username, password);
        this.name = name;

        roles = "ADMIN";
    }

}
