package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Administrator;
import jakarta.transaction.Transactional;

@Transactional
public interface AdminstratorsRepo extends AbstractRepo<Administrator> {

}
