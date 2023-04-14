package com.example.easy_flow_backend.unit_test.utility;

import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.graph.GraphWeightService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected UserRepositry userRepositry;
    @Autowired
    protected PassengersRepo passengersRepo;
    @Autowired
    protected WalletRepo walletRepo;
    @Autowired
    protected StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    protected OwnerRepo ownerRepo;
    @Autowired
    protected LineRepo lineRepo;
    @Autowired
    protected StationRepo stationRepo;
    @Autowired
    protected TripRepo tripRepository;
    @Autowired
    protected TicketRepo ticketRepository;
    @Autowired
    protected GraphRepo graphRepo;
    @Autowired
    protected GraphEdgeRepo graphEdgeRepo;
    @Autowired
    protected GraphWeightService singleLineGraphService;
}
