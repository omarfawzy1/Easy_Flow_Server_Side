package com.easy_flow_server.service.payment_services;

import com.easy_flow_server.entity.Wallet;

public interface WalletService {
    boolean withdraw(Wallet wallet, double value);

    boolean withdrawWithNegative(Wallet wallet, double value);

    boolean canWithdraw(Wallet wallet, double value);

    void recharge(String walletId, double amount);
}
