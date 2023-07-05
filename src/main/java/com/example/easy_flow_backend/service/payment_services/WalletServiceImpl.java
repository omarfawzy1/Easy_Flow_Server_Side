package com.example.easy_flow_backend.service.payment_services;

import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.repos.WalletRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;

    public WalletServiceImpl(WalletRepo walletRepo) {
        this.walletRepo = walletRepo;
    }

    public boolean withdraw(Wallet wallet, double value) {
        if (!canWithdraw(wallet, value)) {
            return false;
        }

        wallet.setBalance(wallet.getBalance() - value);

        walletRepo.save(wallet);
        return true;
    }

    public boolean canWithdraw(@NotNull Wallet wallet, double value) {
        return wallet.getBalance() >= value;
    }

    @Override
    public void recharge(String walletId, double amount) {
        walletRepo.recharge(walletId, amount);
    }


}
