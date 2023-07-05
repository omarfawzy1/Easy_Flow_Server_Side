package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, String> {
    ResetPasswordToken findByToken(String token);

    void deleteAllByExpiryDateAfter(Date expiryDate);
}
