package com.easy_flow_server.repository;

import com.easy_flow_server.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, String> {
    Owner findByName(String name);
    @Override
    boolean existsById(String id);

    <T> List<T> findALLBy(Class<T> type);

    <T> T findByName(String name, Class<T> type);
}
