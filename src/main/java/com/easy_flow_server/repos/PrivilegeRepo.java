package com.easy_flow_server.repos;

import com.easy_flow_server.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, String> {
    Privilege findPrivilegeByNameIgnoreCase(String name);

}
