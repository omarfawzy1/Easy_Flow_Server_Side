package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "plan_id")
    private String id;
    @Column(name="privlage",nullable = false)
    private PassengerPrivlage privlage;
    @Column(name="price",nullable = false)
    private  float price;
    @JoinColumn(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date time;
    @Column(name="trips",nullable = false)
    private int trips;
    @Column(name="name", nullable = false, unique = true)
    private String name;
    @Column(name="max_companion",nullable = false)
    private int maxCompanion;
    @OneToMany(mappedBy = "plan")
    private Set<Subscription> subscriptions= new HashSet<>();
    @Column(name = "available")
    private boolean available=true;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
