package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    private String id;

    @ManyToOne
    @JoinColumn(name="passenger_id",nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name="start_station_id",referencedColumnName = "station_id",nullable = false)
    private Station startStation;

    @ManyToOne
    @JoinColumn(name="end_station_id",referencedColumnName = "station_id",nullable = false)
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
    public Ticket(){};

    public Ticket(String id, Passenger passenger, Station startStation, Station endStation, Date date, Date startTime, Date endTime, float price) {
        this.id = id;
        this.passenger = passenger;
        this.startStation = startStation;
        this.endStation = endStation;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
    public String getId() {
        return id;
    }

    public void setId(String ticket_id) {
        this.id = ticket_id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger_fk) {
        this.passenger = passenger_fk;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station start_station_id) {
        this.startStation = start_station_id;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station end_station_id) {
        this.endStation = end_station_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Float.compare(ticket.price, price) == 0 && Objects.equals(id, ticket.id) && Objects.equals(passenger, ticket.passenger) && Objects.equals(startStation, ticket.startStation) && Objects.equals(endStation, ticket.endStation) && Objects.equals(date, ticket.date) && Objects.equals(startTime, ticket.startTime) && Objects.equals(endTime, ticket.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passenger, startStation, endStation, date, startTime, endTime, price);
    }
}
