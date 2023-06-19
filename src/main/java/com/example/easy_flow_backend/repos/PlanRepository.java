package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
    <T> T findBy(Class<T> type);

    <T> T findById(String Id, Class<T> type);

    <T> List<T> findAllBy(Class<T> type);

    <T> List<T> findAllByOwnerName(String ownerName, Class<T> type);

}
