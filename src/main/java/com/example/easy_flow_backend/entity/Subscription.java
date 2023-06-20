package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"plan_id", "passenger_id"})})
public class Subscription {
    public Subscription(int remainingTrips, Date expireDate, boolean repurchase, Plan plan, Passenger passenger) {

        this.remainingTrips = remainingTrips;
        this.expireDate = expireDate;
        this.repurchase = repurchase;
        this.plan = plan;
        this.passenger = passenger;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "subscription_id")
    private String id;
    @Column(name = "remaining_trips")
    private int remainingTrips;
    @JoinColumn(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date expireDate;
    @Column(name = "repurchase")
    private boolean repurchase ;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "plan_id")
    private Plan plan;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;


    public boolean isExpired() {
        return new Date().after(this.getExpireDate());
    }

    public boolean withdrawTrips(int numberOfTrips) {
        if (numberOfTrips > this.remainingTrips) return false;
        this.remainingTrips -= numberOfTrips;
        return true;
    }

}
