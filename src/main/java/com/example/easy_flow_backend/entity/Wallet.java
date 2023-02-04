package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;
    private float balance;
    @Column(name = "credit_card")
    private String creditCard;
    public Wallet(String creditCard){
        this.balance=0.0f;
        this.creditCard=creditCard;
    }
}
