package com.example.easy_flow_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Station {
    @Id
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;


    public Station() {

    }

    public Station(String id, String stationName) {
        this.id = id;
        this.stationName = stationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String stationId) {
        this.id = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id) && Objects.equals(stationName, station.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationName);
    }
}