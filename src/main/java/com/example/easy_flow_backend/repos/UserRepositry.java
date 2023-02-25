package com.example.easy_flow_backend.repos;

import jakarta.transaction.Transactional;
import com.example.easy_flow_backend.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

@Transactional
public interface UserRepositry extends AbstractRepo<User>
{
@Query("select user.active, count(user.id) " +
        "FROM User user " +
        "WHERE user.roles LIKE '%TURNSTILE%' group by user.active")
    Object getTurnstilesStatus();
}

