package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Stationary_turnstile extends User {

    @ManyToOne
    @JoinColumn(name="station_id", nullable = false)
    private Station station;

    public Stationary_turnstile() {
    }
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stationary_turnstile that = (Stationary_turnstile) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, station);
    }
}
