package com.example.easy_flow_backend.repos;

import jakarta.transaction.Transactional;
import com.example.easy_flow_backend.entity.User;
@Transactional
public interface UserRepositry extends AbstractRepo<User>
{

}

