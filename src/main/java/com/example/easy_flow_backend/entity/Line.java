package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    static long counter=0;
    @Id
    @Column(name = "line_id")
    protected String id;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "line_station",
            joinColumns = @JoinColumn(name = "line_id", referencedColumnName = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id", referencedColumnName = "station_id"))
    Set<Station> stations=new HashSet<>();

    public Line(float price, Owner owner) {
        this.price = price;
        this.owner = owner;
        this.id="line-"+ ++counter;
    }
    public void addStation(Station station){
        stations.add(station);
    }
    public void removeStation(Station station){
        stations.remove(station);
    }
    public Set<Station> getStation(){
        return stations;
    }
}
