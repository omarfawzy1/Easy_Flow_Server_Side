package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
public class ResetPasswordToken {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "passenger_id")
    private Passenger passenger;

    private Date expiryDate;

    public ResetPasswordToken(Passenger passenger, String token) {
        this.token = token;
        this.passenger = passenger;
    }
}
