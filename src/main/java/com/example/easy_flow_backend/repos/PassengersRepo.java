package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Passenger;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PassengersRepo extends AbstractRepo<Passenger> {

}
