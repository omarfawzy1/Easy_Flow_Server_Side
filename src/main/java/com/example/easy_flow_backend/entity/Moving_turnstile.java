package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Moving_turnstile {
    @Id
    private String machine_id;

    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name= "line_id")
    private Line line_fk;

    public Moving_turnstile() {
    }

    public Moving_turnstile(String machine_id, String username, String password, Line line_id) {
        this.machine_id = machine_id;
        this.username = username;
        this.password = password;
        this.line_fk = line_id;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
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

    public Line getLine_id() {
        return line_fk;
    }

    public void setLine_id(Line line_id) {
        this.line_fk = line_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moving_turnstile that = (Moving_turnstile) o;
        return Objects.equals(machine_id, that.machine_id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(line_fk, that.line_fk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machine_id, username, password, line_fk);
    }
}
