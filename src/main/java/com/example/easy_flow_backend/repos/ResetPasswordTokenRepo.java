package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, String> {

}
