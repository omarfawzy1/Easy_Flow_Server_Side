package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private TransportationType type;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "line_station",
            joinColumns = @JoinColumn(name = "line_id", referencedColumnName = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id", referencedColumnName = "station_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Station> stations = new HashSet<>();

    @OneToMany(mappedBy = "line")
    private Set<MovingTurnstile> movingTurnstiles= new HashSet<>();
    @OneToMany(mappedBy = "line", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets= new HashSet<>();
    @OneToMany(mappedBy = "line", cascade = CascadeType.REMOVE)//TODO i convert cascadeTypeALL to CascadeType Remove
    private Set<GraphEdge> graphEdges= new HashSet<>();

    public Line(String name, TransportationType type, Owner owner) {
        this.name = name;
        this.type = type;
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
        return type.equals(line.type) && name.equals(line.name) && owner.equals(line.owner) && Objects.equals(stations, line.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, owner, stations);
    }
}
