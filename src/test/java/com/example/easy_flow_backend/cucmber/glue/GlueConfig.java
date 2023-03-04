package com.example.easy_flow_backend.cucmber.glue;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
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
    protected TripRepo tripRepo;
    @Autowired
    protected UserRepositry userRepositry;
    @Autowired
    protected WalletRepo walletRepo;

    protected List<Passenger> passengerList=new ArrayList<>();
    protected List<Object> expected=new ArrayList<>();
    protected List<Object> actual=new ArrayList<>();
    protected Gson gson=new Gson();

    @DataTableType
    protected Wallet walletEntry(Map<String, String> entry) {
        return new Wallet(
                entry.get("creditCard"),
                Double.valueOf(entry.get("balance")));
    }
    @DataTableType
    protected Passenger passengerEntry(Map<String, String> entry) {
        return new Passenger(
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("phoneNumber"),
                entry.get("type"),
                entry.get("city"),
                Gender.valueOf(entry.get("gender")),
                Date.valueOf(entry.get("birthDay")),
                entry.get("username"),
                passwordEncoder.encode(entry.get("password")),
                entry.get("email"));
    }
    @DataTableType
    protected Administrator administratorEntry(Map<String, String> entry) {
        return new Administrator(
                entry.get("name"),
                entry.get("username"),
                passwordEncoder.encode(entry.get("password")));
    }
    public void match(List<Object>l1, List<Object>l2)
    {
        List<Object>list= (List<Object>) l2.get(0);
        for (int i=0; i<l1.size(); i++){
            boolean found=false;
            for (int j=0;j<list.size();j++){
                if(actual.get(i).equals(list.get(j))){
                    found=true;
                    list.remove(j);
                }
            }
            Assertions.assertTrue(found);
            found=false;
        }
    }
}
