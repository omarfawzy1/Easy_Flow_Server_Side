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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "graph_id", "owner_id" }) })
public class Graph {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "graph_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;




}
