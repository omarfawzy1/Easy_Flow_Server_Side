package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Wallet;

public interface WalletService {
    boolean withdraw(Wallet wallet, double value);

    boolean canWithdraw(Wallet wallet, double value);

    void recharge(String walletId, double amount);
}
