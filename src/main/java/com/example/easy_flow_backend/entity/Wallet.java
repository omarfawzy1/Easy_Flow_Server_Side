package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "wallet_id")
    private String id;
    private float balance;
    @Column(name = "credit_card")
    private String creditCard;

    public Wallet(String creditCard) {
        this.balance = 0.0f;
        this.creditCard = creditCard;
    }
    public Wallet(String creditCard, float balance) {
        this.creditCard = creditCard;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Float.compare(wallet.balance, balance) == 0  && creditCard.equals(wallet.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, creditCard);
    }
}
