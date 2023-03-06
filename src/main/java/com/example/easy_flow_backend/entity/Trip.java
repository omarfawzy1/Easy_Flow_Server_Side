package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "trip_id")
    private String id;

    @ManyToOne

    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "start_turnstile_id", nullable = false)
    private Turnstile startTurnstile;

    @ManyToOne
    @JoinColumn(name = "end_turnstile_id", nullable = true)
    private Turnstile endTurnstile;
    @JoinColumn(name = "start_time",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startTime;

    @JoinColumn(name = "end_time",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endTime;

    @Enumerated
    @Column(name= "transportation_type")
    private TransportationType transportationType;

    @JoinColumn(nullable = false)
    private double price;

    @JoinColumn(nullable = false)
    private Status status;
    public Trip(Passenger passenger, Turnstile startTurnstile, Date startTime, Status status) {
        this.passenger = passenger;
        this.startTurnstile = startTurnstile;
        this.startTime = startTime;
        this.status = status;
        this.price = 0;
    }

    public Trip(Passenger passenger, Turnstile startTurnstile, Turnstile endTurnstile, Date startTime, Date endTime, double price, Status status) {
        this.passenger = passenger;
        this.startTurnstile = startTurnstile;
        this.endTurnstile = endTurnstile;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
    }
}