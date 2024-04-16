package com.easy_flow_server.repository;

import com.easy_flow_server.dto.view.PassagnerBriefDetails;
import com.easy_flow_server.dto.view.PassagnerDetails;
import com.easy_flow_server.entity.Passenger;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface PassengerRepo extends AbstractRepo<Passenger> {

    List<PassagnerBriefDetails> findAllProjectedBy();

    Passenger findPassengerByPhoneNumberAndPin(String PhoneNumber, String pin);

    PassagnerDetails findAllProjectedByUsername(String username);

    Passenger findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    void deleteByUsernameIgnoreCase(String username);

    PassagnerDetails findProjectedByUsername(String passenger_username);

    @Query(value = "SELECT count(pp.passenger_id) " +
            "from passenger_privilege pp join privilege p on p.privilege_id = pp.privilege_id " +
            "where p.name = :privilege ",
            nativeQuery = true)
    int getPassengersCountWithPrivilege(@Param("privilege") String privilege);

    @Query("select COUNT (passenger.id)" +
            "From Passenger passenger " +
            "WHERE passenger.wallet.balance < 0")
    int getNegativePassengerCount();

    @Query("select COUNT (passenger.id)" +
            "From Passenger passenger " +
            "WHERE passenger.wallet.balance < :threshold")
    int getBelowThresholdCount(@Param("threshold") long threshold);

    Passenger findByEmailIgnoreCase(String email);
}
