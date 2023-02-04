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
    @Column(name = "wallet_id")
    private String id;
    private float balance;
    @Column(name = "credit_card")
    private String creditCard;
}
