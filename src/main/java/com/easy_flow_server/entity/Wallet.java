package com.easy_flow_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private double balance;
    @Column(name = "credit_card")
    private String creditCard;

    public Wallet(String creditCard) {
        this.balance = 0.0;
        this.creditCard = creditCard;
    }
    public Wallet(String creditCard, Double balance) {
        this.creditCard = creditCard;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Double.compare(wallet.balance, wallet.balance) == 0  && creditCard.equals(wallet.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, creditCard);
    }
}
