package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GraphEdge {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;
    @ManyToOne
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @ManyToOne
    @JoinColumn(name = "from_station_id")
    private Station fromStation;

    @ManyToOne
    @JoinColumn(name = "to_station_id")
    private Station toStation;

    private double weight;
}
