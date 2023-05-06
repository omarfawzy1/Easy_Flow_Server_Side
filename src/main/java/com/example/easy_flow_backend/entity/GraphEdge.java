package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor

@Setter
@Getter
public class GraphEdge {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "from_station_id")
    private Station fromStation;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "to_station_id")
    private Station toStation;

    private double weight;

    public GraphEdge(Graph graph, Station fromStation, Station toStation, double weight) {
        this.graph = graph;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.weight = weight;
    }
}
