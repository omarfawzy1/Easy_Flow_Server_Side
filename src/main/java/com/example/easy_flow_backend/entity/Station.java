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
public class Station {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;
    @ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY)
    Set<Line> lines = new HashSet<>();

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
        return stationName.equals(station.stationName) && equalsLines((Line[]) ((Station) o).getLines().toArray());
    }

    private boolean equalsLines(Line[] lines) {
        if (lines.length != this.lines.size()) return false;
        for (int i = 0; i < this.lines.size(); i++) {
            if (!this.lines.contains(lines[i])) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName);
    }
}
