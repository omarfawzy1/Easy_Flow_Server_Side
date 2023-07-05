package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Turnstile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TurnstileRepo<T extends Turnstile> extends AbstractRepo<T> {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
