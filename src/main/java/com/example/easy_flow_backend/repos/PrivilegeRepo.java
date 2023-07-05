package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, String> {
    Privilege findPrivilegeByNameIgnoreCase(String name);

}
