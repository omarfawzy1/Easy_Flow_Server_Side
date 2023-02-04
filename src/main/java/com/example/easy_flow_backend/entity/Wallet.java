package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Wallet {
    @Id
    @Column(name = "wallet_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private float balance;
    @Column(name = "credit_card")
    private String creditCard;

    public Wallet(float balance, String creditCard) {
        this.balance=balance;
        this.creditCard=creditCard;
    }
}
