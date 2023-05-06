package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"line_id", "owner_id"})},
//        indexes = {@Index(columnList = "owner_id"), @Index(columnList = "owner_id,line_id") })
@Table(indexes = {@Index(columnList = "owner_id")})
public class Graph {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "graph_id")
    private String id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "line_id")
    private Line line;
    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL)
    private Set<GraphEdge> graphEdges= new HashSet<>();


}
