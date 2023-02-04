package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    @Id
    @Column(name = "line_id")
    private String id;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToMany
    @JoinTable(
            name = "line_station",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    Set<Station> stations;
}
