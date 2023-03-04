package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GraphEdge {
    @Id
    @ManyToOne
    @JoinColumn(name = "graph_id")
    private Graph graph;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "from_station_id")
    private Station fromStation;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "to_station_id")
    private Station toStation;

    private double weight;
}
