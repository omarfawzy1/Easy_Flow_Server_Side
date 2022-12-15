package com.example.easy_flow_backend.entiry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Administrator {
    @Id
    private String admin_id;
    private String name;
    private String username;
    private String password;

    public Administrator(){};
    public Administrator(String admin_id, String name, String username, String password) {
        this.admin_id = admin_id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(admin_id, that.admin_id) && Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin_id, name, username, password);
    }
}
