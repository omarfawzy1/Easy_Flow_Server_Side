package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    static long counter=0;
    @Id
    @Column(name = "station_id")
    private String id;
    @Column(name = "station_name", nullable = false, unique = true)
    private String stationName;
    @ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY)
    Set<Line> lines=new HashSet<>();

    public Station(String stationName){

        this.stationName=stationName;
        this.id="station-"+ ++counter;
    }
    public void addLine(Line line){
        lines.add(line);
    }
    public void removeStation(Line line){
        lines.remove(line);
    }
    public Set<Line> getLine(){
        return lines;
    }

}
