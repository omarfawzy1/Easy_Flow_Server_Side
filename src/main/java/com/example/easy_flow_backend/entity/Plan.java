package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","owner_id"})})
public class Plan {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "plan_id")
    private String id;
    @Column(name = "privilege", nullable = false)
    private PassengerPrivilege privilege;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "duration")
    private int durationDays;

    @Column(name = "trips", nullable = false)
    private int numberOfTrips;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "max_companion", nullable = false)
    private int maxCompanion;
    @OneToMany(mappedBy = "plan")
    private Set<Subscription> subscriptions = new HashSet<>();
    @Column(name = "available")
    private boolean available = true;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "discount_rate")
    @Range(min = 0, max = 1)
    private float discountRate;

    public Plan(PassengerPrivilege privilege, float price, int durationDays, int numberOfTrips, String name, int maxCompanion, Owner owner, float discountRate) {
        this.privilege = privilege;
        this.price = price;
        this.durationDays = durationDays;
        this.numberOfTrips = numberOfTrips;
        this.name = name;
        this.maxCompanion = maxCompanion;
        this.owner = owner;
        this.discountRate = discountRate;
    }
}
