package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.repos.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepo walletRepo;

    public boolean withdraw(Wallet wallet, double value) {
        if (!canWithdraw(wallet, value)) {
            return false;
        }

        wallet.setBalance(wallet.getBalance() - value);

        walletRepo.save(wallet);
        return true;
    }

    public boolean canWithdraw(Wallet wallet, double value) {
        return wallet.getBalance() >= value;
    }

    @Override
    public void recharge(String walletId, double amount) {
        walletRepo.recharge(walletId, amount);
    }


}
