package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
public class ResetPasswordToken {
    private static final long EXPIRATION = 1000*60*5;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "passenger_id")
    private Passenger passenger;

    private Date expiryDate;

    public ResetPasswordToken(Passenger passenger, String token) {
        this.token = token;
        this.passenger = passenger;
        this.expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime()+EXPIRATION);
    }
}
