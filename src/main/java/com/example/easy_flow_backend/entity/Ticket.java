package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @Id
    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;
    private double price;
    private double weight;
    private Date time;
}
