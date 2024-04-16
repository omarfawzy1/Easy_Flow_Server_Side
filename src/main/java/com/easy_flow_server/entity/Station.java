package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Station {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;
    @Column(name = "latitude")
    private Float latitude;
    @Column(name = "longitude")
    private Float longitude;
    @ManyToMany(mappedBy = "stations", fetch = FetchType.EAGER)
    Set<Line> lines = new HashSet<>();
    @OneToMany(mappedBy = "fromStation", cascade = CascadeType.ALL)
    private Set<GraphEdge> fromGraphEdges = new HashSet<>();
    @OneToMany(mappedBy = "toStation", cascade = CascadeType.ALL)
    private Set<GraphEdge> toGraphEdges = new HashSet<>();
    @OneToMany(mappedBy = "station")
    private Set<StationaryTurnstile> stationaryTurnstiles = new HashSet<>();

    public Station(String stationName) {

        this.stationName = stationName;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void removeStation(Line line) {
        lines.remove(line);
    }

    public Set<Line> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return stationName.equals(station.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName);
    }
}
