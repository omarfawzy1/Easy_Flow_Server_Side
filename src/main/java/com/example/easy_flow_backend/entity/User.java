package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    protected String id;
    @Column(nullable = false)
    protected String username;
    @Column(nullable = false)
    @JsonIgnore
    protected String password;
    @Column(nullable = false)
    protected boolean active = true; // TODO set to false by default until gmail verification
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String roles = "";
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String permissions = "";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}