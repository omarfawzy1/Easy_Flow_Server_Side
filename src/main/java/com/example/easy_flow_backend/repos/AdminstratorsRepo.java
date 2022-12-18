package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Administrator;
import com.example.easy_flow_backend.entity.Passenger;
import jakarta.transaction.Transactional;

@Transactional
public interface AdminstratorsRepo extends AbstractRepo<Administrator> {

}
