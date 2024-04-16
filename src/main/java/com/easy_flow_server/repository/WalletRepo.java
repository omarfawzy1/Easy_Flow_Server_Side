package com.easy_flow_server.repository;

import com.easy_flow_server.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, String> {

    @Modifying
    @Transactional
    @Query("update Wallet wallet set wallet.balance =wallet.balance+ :amount " +
            "WHERE wallet.id = :id")
    void recharge(@Param("id")String walletId, @Param("amount") double amount);
}
