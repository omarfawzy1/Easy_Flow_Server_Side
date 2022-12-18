package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Stationary_turnstile extends User {
    @Id
    private String id;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name="station_id")
    private Station Station_fk;


    public Stationary_turnstile() {
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

    public Station getStation_fk() {
        return Station_fk;
    }

    public void setStation_fk(Station station_fk) {
        Station_fk = station_fk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stationary_turnstile that = (Stationary_turnstile) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(Station_fk, that.Station_fk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, Station_fk);
    }
}
