package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "line_id")
    protected String id;

    @Column(name = "line_name", nullable = true, unique = true)
    private String name;
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "line_station",
            joinColumns = @JoinColumn(name = "line_id", referencedColumnName = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id", referencedColumnName = "station_id"))
    Set<Station> stations = new HashSet<>();

    @OneToOne(optional = true)
    @JoinColumn(name = "graph_id", referencedColumnName ="graph_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Graph graph;

    public Line(String name, double price, Owner owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void removeStation(Station station) {
        stations.remove(station);
    }

    public Set<Station> getStations() {
        return stations;
    }

    public Set<String> getStationsId() {
        Set<String> stationIds = new HashSet<>();
        for (Station station : this.stations) {
            stationIds.add(station.getId());
        }
        return stationIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.price, price) == 0 && name.equals(line.name) && owner.equals(line.owner) && Objects.equals(stations, line.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, owner, stations);
    }
}
