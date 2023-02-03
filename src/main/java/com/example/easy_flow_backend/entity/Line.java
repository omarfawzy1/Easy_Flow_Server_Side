package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Line {
    @Id
    @Column(name = "line_id")
    private String id;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner;
    @ManyToMany
    @JoinTable(
            name = "line_station",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    Set<Station> stations=null;

    public Line() {

    }

    public Line(String id, float price, Owner owner) {
        this.id = id;
        this.price = price;
        this.owner = owner;
        stations=new HashSet<>();

    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }
    public void addStation(Station station) {
        this.stations.add(station);
    }
    public void removeStation(Station station){
        this.stations.remove(station);
    }
    public Set<Station> getStations() {
        return stations;
    }

    public String getId() {
        return id;
    }

    public void setId(String line_id) {
        this.id = line_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner_fk) {
        this.owner = owner_fk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Float.compare(line.price, price) == 0 && Objects.equals(id, line.id) && Objects.equals(owner, line.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, owner);
    }
}
