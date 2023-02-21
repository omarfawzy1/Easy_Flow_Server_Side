package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;
import java.util.Date;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ticket_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "start_station_id", referencedColumnName = "station_id", nullable = false)
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id", referencedColumnName = "station_id", nullable = true)
    private Station endStation;
    @JoinColumn(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @JoinColumn(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @JoinColumn(nullable = true)
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @JoinColumn(nullable = false)
    private double price;

    @JoinColumn(nullable = false)
    private Status status;
    public Ticket(Passenger passenger, Station startStation, Date date, LocalTime startTime, Status status) {
        this.passenger = passenger;
        this.startStation = startStation;
        this.date = date;
        this.startTime = startTime;
        this.status = status;
        this.price = 0;
    }

    public Ticket(Passenger passenger, Station startStation, Station endStation, Date date, LocalTime startTime, LocalTime endTime, double price, Status status) {
        this.passenger = passenger;
        this.startStation = startStation;
        this.endStation = endStation;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
    }


}
