package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepo<T extends User> extends JpaRepository<T, String>
{
//    @Query("select e from #{#entityName} as e from user where e.useranme = useranme")

    public T findUserByUsername(String username);
}