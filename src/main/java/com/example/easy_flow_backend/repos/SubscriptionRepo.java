package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, String> {
    List<Subscription> getSubscriptionByPassengerIdAndPlanOwnerName(String passenger_id, String plan_owner_name);

    List<Subscription> getAllByExpireDateBefore(Date expireDate);

    <T> List<T> findAllByPassengerUsername(String passenger_username, Class<T> type);
}
