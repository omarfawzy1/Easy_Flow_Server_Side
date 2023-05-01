package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.repos.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;

    public void recharge(String walletId, double amount){
        walletRepo.recharge(walletId, amount);
    }
}
