package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"station_name", "transportation_type"})})
public class Station {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false)
    private String stationName;
    @ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY)
    Set<Line> lines = new HashSet<>();

//    @Enumerated
//    @Column(name= "transportation_type")
//    private TransportationType transportationType;

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
