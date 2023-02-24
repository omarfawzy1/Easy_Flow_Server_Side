package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.dto.Views.PassagnerDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface PassengersRepo extends AbstractRepo<Passenger> {

    List<PassagnerDetails> findAllProjectedBy();


    Passenger findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByUsernameIgnoreCase(String username);

    PassagnerDetails findProjectedByUsername(String passenger_username);

    int findByTypeIgnoreCase(String type);

    @Query("SELECT COUNT (passenger) FROM Passenger  passenger WHERE passenger.type = :type")
    int getAllPassangersCountWithType(@Param("type") String type);
}
