package com.example.easy_flow_backend.entiry;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    private String ticket_id;

    @ManyToOne
    @JoinColumn(name="passenger_id")
    private Passenger passenger_fk;

    @ManyToOne
    @JoinColumn(name="start_station_id",referencedColumnName = "station_id")
    private Station start_station_id;

    @ManyToOne
    @JoinColumn(name="end_station_id",referencedColumnName = "station_id")
    private Station end_station_id;

    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @Temporal(TemporalType.TIME)
    private java.util.Date start_time;

    @Temporal(TemporalType.TIME)
    private java.util.Date end_time;

    private float price;
    public Ticket(){};

    public Ticket(String ticket_id, Passenger passenger_fk, Station start_station_id, Station end_station_id, Date date, Date start_time, Date end_time, float price) {
        this.ticket_id = ticket_id;
        this.passenger_fk = passenger_fk;
        this.start_station_id = start_station_id;
        this.end_station_id = end_station_id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
    }
    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Passenger getPassenger_fk() {
        return passenger_fk;
    }

    public void setPassenger_fk(Passenger passenger_fk) {
        this.passenger_fk = passenger_fk;
    }

    public Station getStart_station_id() {
        return start_station_id;
    }

    public void setStart_station_id(Station start_station_id) {
        this.start_station_id = start_station_id;
    }

    public Station getEnd_station_id() {
        return end_station_id;
    }

    public void setEnd_station_id(Station end_station_id) {
        this.end_station_id = end_station_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
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
        return Float.compare(ticket.price, price) == 0 && Objects.equals(ticket_id, ticket.ticket_id) && Objects.equals(passenger_fk, ticket.passenger_fk) && Objects.equals(start_station_id, ticket.start_station_id) && Objects.equals(end_station_id, ticket.end_station_id) && Objects.equals(date, ticket.date) && Objects.equals(start_time, ticket.start_time) && Objects.equals(end_time, ticket.end_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket_id, passenger_fk, start_station_id, end_station_id, date, start_time, end_time, price);
    }
}
