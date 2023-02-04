package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "start_station_id", referencedColumnName = "station_id", nullable = false)
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id", referencedColumnName = "station_id", nullable = false)
    private Station endStation;
    @JoinColumn(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @JoinColumn(nullable = false)
    @Temporal(TemporalType.TIME)
    private java.util.Date startTime;
    @JoinColumn(nullable = false)
    @Temporal(TemporalType.TIME)
    private java.util.Date endTime;
    @JoinColumn(nullable = false)
    private float price;
}
