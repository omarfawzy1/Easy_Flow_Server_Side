package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.view.PassagnerDetails;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Transactional
@Repository
public interface PassengersRepo extends AbstractRepo<Passenger> {

    public List<PassagnerDetails> findAllProjectedBy();

    public boolean existsByUsernameIgnoreCase(String username);
    public boolean existsByPhoneNumber(String phoneNumber);
}
