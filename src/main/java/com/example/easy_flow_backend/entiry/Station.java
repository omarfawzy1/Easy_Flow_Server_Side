package com.example.easy_flow_backend.entiry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Station {
    @Id
    private String station_id;
    private String station_name;


    public Station() {

    }

    public Station(String station_id, String station_name) {
        this.station_id = station_id;
        this.station_name = station_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(station_id, station.station_id) && Objects.equals(station_name, station.station_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station_id, station_name);
    }
}
