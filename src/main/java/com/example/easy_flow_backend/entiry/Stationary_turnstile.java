package com.example.easy_flow_backend.entiry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Stationary_turnstile {
    @Id
    private String machine_id;

    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name="station_id")
    private Station Station_fk;


    public Stationary_turnstile() {
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
        return Objects.equals(machine_id, that.machine_id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(Station_fk, that.Station_fk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machine_id, username, password, Station_fk);
    }
}
