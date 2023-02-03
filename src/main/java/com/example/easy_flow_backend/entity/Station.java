package com.example.easy_flow_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Station {
    @Id
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;
    @ManyToMany(mappedBy = "stations")
    Set<Line> lines;


    public Station() {

    }

    public Station(String id, String stationName) {
        this.id = id;
        this.stationName = stationName;
        lines =new HashSet<>();
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
    public void setLines(Set<Line> line) {
        this.lines = line;
    }
    public void addLine(Line line) {
        this.lines.add(line);
    }
    public void removeLine(Line line){
        this.lines.remove(line);
    }
    public Set<Line> getLines() {
        return lines;
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
