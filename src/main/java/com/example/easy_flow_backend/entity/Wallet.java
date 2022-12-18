package com.example.easy_flow_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Wallet {
    @Id
    private String wallet_id;
    private float balance;
    private String credit_card;
    public Wallet(){};

    public Wallet(String wallet_id, float balance, String credit_card) {
        this.wallet_id = wallet_id;
        this.balance = balance;
        this.credit_card = credit_card;
    }

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Float.compare(wallet.balance, balance) == 0 && Objects.equals(wallet_id, wallet.wallet_id) && Objects.equals(credit_card, wallet.credit_card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wallet_id, balance, credit_card);
    }
}
