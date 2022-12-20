package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name ="Stationary_turnstile")
public class StationaryTurnstile extends User {

    @ManyToOne
    @JoinColumn(name="station_id", nullable = false)
    private Station station;

    public StationaryTurnstile() {
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
        StationaryTurnstile that = (StationaryTurnstile) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, station);
    }
}
