package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    protected String id;
    @Column(nullable = true)
    protected String username;
    @Column(nullable = true)
    protected String password;
    protected boolean active = true; // TODO set to false by default until gmail verification
    protected String roles = "";
    protected String permissions = "";

    public User(String id, String username, String password, boolean active, String roles, String permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public List<String> getRoleList() {
        if (!this.roles.isEmpty())
            return Arrays.asList(this.roles.split(","));
        return new ArrayList<>();
    }
    public List<String> getPermessionList() {
        if (!this.permissions.isEmpty())
            return Arrays.asList(this.permissions.split(","));
        return new ArrayList<>();
    }

    public void setUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
        this.active = true;
    }

}