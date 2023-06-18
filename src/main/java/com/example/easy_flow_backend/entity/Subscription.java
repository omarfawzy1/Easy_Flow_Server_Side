package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
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
    private boolean repurchase = false;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "plan_id")
    private Plan plan;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public boolean isExpired() {
        return new Date().before(this.getExpireDate());
    }

    public boolean withdrawTrips(int numberOfTrips) {
        if (numberOfTrips > this.remainingTrips) return false;
        this.remainingTrips -= numberOfTrips;
        return true;
    }

}
