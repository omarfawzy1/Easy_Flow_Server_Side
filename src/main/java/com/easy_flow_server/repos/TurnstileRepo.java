package com.easy_flow_server.repos;

import com.easy_flow_server.entity.Turnstile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TurnstileRepo<T extends Turnstile> extends AbstractRepo<T> {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
