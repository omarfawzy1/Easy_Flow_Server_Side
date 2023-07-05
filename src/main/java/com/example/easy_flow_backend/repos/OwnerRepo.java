package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, String> {
    Owner findByName(String name);
    boolean existsByName(String name);
    @Override
    boolean existsById(String id);

    <T> List<T> findALLBy(Class<T> type);

    <T> T findByName(String name, Class<T> type);
}
