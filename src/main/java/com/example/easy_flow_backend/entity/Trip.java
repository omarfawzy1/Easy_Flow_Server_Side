package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "start_turnstile_id")
    private Turnstile startTurnstile;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "end_turnstile_id")
    private Turnstile endTurnstile;

    @JoinColumn(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startTime;

    @JoinColumn(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endTime;

    @Enumerated
    @Column(name = "transportation_type")
    private TransportationType transportationType;

    @JoinColumn(nullable = false)
    private double price;

    @JoinColumn(nullable = false)
    private Status status;

    @Column(name = "start_station")
    private String startStation;

    @Column(name = "end_station")
    private String endStation;

    public Trip(Passenger passenger, Turnstile startTurnstile, String startStation, TransportationType transportationType, Date startTime, Status status) {
        this.passenger = passenger;
        this.startTurnstile = startTurnstile;
        this.startTime = startTime;
        this.status = status;
        this.startStation = startStation;
        this.transportationType = transportationType;
        this.price = 0;
    }

    public Trip(Passenger passenger, Turnstile startTurnstile, Turnstile endTurnstile, Date startTime, Date endTime, TransportationType transportationType, double price, Status status, String startStationName, String endStationName) {
        this.passenger = passenger;
        this.startTurnstile = startTurnstile;
        this.endTurnstile = endTurnstile;
        this.startTime = startTime;
        this.endTime = endTime;
        this.transportationType = transportationType;
        this.price = price;
        this.status = status;
        this.startStation = startStationName;
        this.endStation = endStationName;
    }

    public Trip(Passenger passenger, Status status) {
        this.passenger = passenger;
        this.status = status;
    }

}
