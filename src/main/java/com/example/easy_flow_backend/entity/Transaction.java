package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "transaction_id")
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id", referencedColumnName ="id")
    @Cascade(CascadeType.ALL)
    private Passenger passenger;
    @Column(nullable = false)
    private double amount;
}
