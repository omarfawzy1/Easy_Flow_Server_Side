package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class LineStations {
    public LineStations(){};
    @Id
    @ManyToOne
    @JoinColumn(name= "line_id")
    private Line line;

    @Id
    @ManyToOne
    @JoinColumn(name= "station_id")
    private Line station;

    @Column(nullable = false)
    private int station_order;

    public LineStations(Line line, Line station, int station_order) {
        this.line = line;
        this.station = station;
        this.station_order = station_order;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getStation() {
        return station;
    }

    public void setStation(Line station) {
        this.station = station;
    }

    public int getStation_order() {
        return station_order;
    }

    public void setStation_order(int station_order) {
        this.station_order = station_order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStations that = (LineStations) o;
        return station_order == that.station_order && Objects.equals(line, that.line) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, station, station_order);
    }
}
