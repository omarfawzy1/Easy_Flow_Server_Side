package com.example.easy_flow_backend.entiry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class LineStations {
    public LineStations(){};
    @Id
    @ManyToOne
    @JoinColumn(name= "line_id")
    private Line line_fk;

    @Id
    @ManyToOne
    @JoinColumn(name= "station_id")
    private Line station_fk;

    private int station_order;

    public LineStations(Line line_fk, Line station_fk, int station_order) {
        this.line_fk = line_fk;
        this.station_fk = station_fk;
        this.station_order = station_order;
    }

    public Line getLine_fk() {
        return line_fk;
    }

    public void setLine_fk(Line line_fk) {
        this.line_fk = line_fk;
    }

    public Line getStation_fk() {
        return station_fk;
    }

    public void setStation_fk(Line station_fk) {
        this.station_fk = station_fk;
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
        return station_order == that.station_order && Objects.equals(line_fk, that.line_fk) && Objects.equals(station_fk, that.station_fk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line_fk, station_fk, station_order);
    }
}
