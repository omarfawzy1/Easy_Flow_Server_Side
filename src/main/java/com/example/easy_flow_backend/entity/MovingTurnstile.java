package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Moving_turnstile")
public class MovingTurnstile extends User{

    @ManyToOne
    @JoinColumn(name= "line_id")
    private Line line;

    public MovingTurnstile() {
    }

    public MovingTurnstile(String id, String username, String password, Line line) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.line = line;
    }

    public String getId() {
        return id;
    }

    public void setId(String machine_id) {
        this.id = machine_id;
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
        return line;
    }

    public void setLine_id(Line line_id) {
        this.line = line_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovingTurnstile that = (MovingTurnstile) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, line);
    }
}
