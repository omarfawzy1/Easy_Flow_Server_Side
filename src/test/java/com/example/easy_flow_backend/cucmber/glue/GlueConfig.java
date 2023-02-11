package com.example.easy_flow_backend.cucmber.glue;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.repos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.messages.internal.com.google.gson.Gson;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ComponentScan({"com.example.easy_flow_backend"})
@SpringBootTest

public class GlueConfig {
    protected TestRestTemplate testRestTemplate =new TestRestTemplate();
    protected Map<String, String> params = new HashMap<>();
    protected HttpHeaders headers = new HttpHeaders();
    protected String token;

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected AdminstratorsRepo adminstratorsRepo;
    @Autowired
    protected LineRepo lineRepo;
    @Autowired
    protected MovingTurnstileRepo movingTurnstileRepo;
    @Autowired
    protected OwnerRepo ownerRepo;
    @Autowired
    protected PassengersRepo passengersRepo;
    @Autowired
    protected StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    protected StationRepo stationRepo;
    @Autowired
    protected TicketRepo ticketRepo;
    @Autowired
    protected UserRepositry userRepositry;
    @Autowired
    protected WalletRepo walletRepo;

    protected List<Passenger> passengerList=new ArrayList<>();
    protected List<Object> expected=new ArrayList<>();
    protected List<Object> actual=new ArrayList<>();
    protected Gson gson=new Gson();
}
