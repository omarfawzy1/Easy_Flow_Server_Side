package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "line_id")
    private Line line;
    private double price;
    private double weight;
    private long time;

    public Ticket(Owner owner, Line line, double price, double weight, long time) {
        this.owner = owner;
        this.line = line;
        this.price = price;
        this.weight = weight;
        this.time = time;
    }
}
