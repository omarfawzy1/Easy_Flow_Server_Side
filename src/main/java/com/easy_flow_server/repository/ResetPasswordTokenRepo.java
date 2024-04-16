package com.easy_flow_server.repository;

import com.easy_flow_server.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, String> {
    ResetPasswordToken findByToken(String token);

    void deleteAllByExpiryDateAfter(Date expiryDate);
}
