package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, String> {
    Owner findByName(String name);

    @Override
    boolean existsById(String s);
}
