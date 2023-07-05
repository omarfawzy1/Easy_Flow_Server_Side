package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface UserRepositry extends AbstractRepo<User>
{
@Query("select user.active, count(user.id) " +
        "FROM User user " +
        "WHERE user.roles LIKE '%TURNSTILE%' group by user.active")
    Object getTurnstilesStatus();

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}

