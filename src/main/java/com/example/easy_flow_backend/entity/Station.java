package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    @Id
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;
    @ManyToMany(mappedBy = "stations")
    Set<Line> lines;

}
