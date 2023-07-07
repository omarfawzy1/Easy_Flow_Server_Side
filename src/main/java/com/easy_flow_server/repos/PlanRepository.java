package com.easy_flow_server.repos;

import com.easy_flow_server.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
    <T> T findBy(Class<T> type);

    <T> T findById(String Id, Class<T> type);

    <T> List<T> findAllBy(Class<T> type);

    <T> T findByNameAndOwnerName(String name, String owner_name, Class<T> type);

    <T> List<T> findAllByOwnerName(String ownerName, Class<T> type);

    boolean existsByNameAndOwnerName(String name, String owner_name);
    void deleteByNameAndOwnerName(String name, String owner_name);
}
