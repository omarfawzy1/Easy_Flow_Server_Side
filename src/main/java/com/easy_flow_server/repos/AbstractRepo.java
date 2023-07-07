package com.easy_flow_server.repos;

import com.easy_flow_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepo<T extends User> extends JpaRepository<T, String>
{

    public T findUserByUsername(String username);


}