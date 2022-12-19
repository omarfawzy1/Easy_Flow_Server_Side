package com.example.easy_flow_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Wallet {
    @Id
    @Column(name = "wallet_id")
    private String id;
    private float balance;
    @Column(name = "credit_card")
    private String creditCard;
    public Wallet(){};

    public Wallet(String id, float balance, String creditCard) {
        this.id = id;
        this.balance = balance;
        this.creditCard = creditCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Float.compare(wallet.balance, balance) == 0 && Objects.equals(id, wallet.id) && Objects.equals(creditCard, wallet.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, creditCard);
    }
}
