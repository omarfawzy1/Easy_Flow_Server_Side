package com.easy_flow_server.service.payment_services;

import com.easy_flow_server.entity.Wallet;
import com.easy_flow_server.repos.WalletRepo;
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

    public boolean withdrawWithNegative(Wallet wallet, double value) {

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
