package com.example.easy_flow_backend.cucmber.glue;

import com.example.easy_flow_backend.cucmber.CucumberConfig;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import net.minidev.json.JSONObject;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@ComponentScan({"com.example.easy_flow_backend"})
@SpringBootTest

public class AdminGlue extends GlueConfig {

//    private RestTemplate testRestTemplate =new RestTemplate();
//    Map<String, String> params = new HashMap<>();
//    HttpHeaders headers = new HttpHeaders();
//    String token;
//
//    @Autowired
//    private  ObjectMapper objectMapper;
//    @Autowired
//    private UserRepositry userRepositry;
//    private List<Line> expectedLines;
//    private List<Line> actualLines;
//    @Autowired
//    private LineRepo lineRepo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public AdminGlue(){
    }
    @DataTableType
    public Passenger passengerEntry(Map<String, String> entry) {
        return new Passenger(
                entry.get("firstName"),
                entry.get("lastName"),
                entry.get("phoneNumber"),
                entry.get("type"),
                entry.get("city"),
                Gender.valueOf(entry.get("gender")),
                Date.valueOf(entry.get("birthDay")),
                entry.get("username"),
                passwordEncoder.encode(entry.get("password")));
    }
    //String creditCard, float balance
    @DataTableType
    public Wallet walletEntry(Map<String, String> entry) {
        return new Wallet(
                entry.get("creditCard"),
                Float.valueOf(entry.get("balance")));
    }

    @Given("^the admin table in the database contain are the following$")
    public void the_admin_in_the_database_are_the_following(io.cucumber.datatable.DataTable dataTable) {
        adminstratorsRepo.deleteAll();
        Administrator admin;
        for(int i=0;i<dataTable.height();i++){
            admin=new Administrator(dataTable.cell(i,0),
                    dataTable.cell(i,1), passwordEncoder.encode(dataTable.cell(i,2)));
            adminstratorsRepo.save(admin);
        }
    }
    @When("^the admin try to log in with username (.*) and password (.*)$")
    public void the_admin_try_to_log_in(String username, String password) throws URISyntaxException {
        params.put("username",username);
        params.put("password",password);
//        token=testRestTemplate.
//                postForEntity(new URI("http://localhost:8080/login"),params
//                        ,HttpHeaders.class).getHeaders().get("Authorization").get(0);
//        params.clear();
//        headers.setBearerAuth(token);

    }
    @Then("^the server allow this request$")
    public void the_server_allow_this_request() throws URISyntaxException {
        Assertions.assertTrue(testRestTemplate.
                postForEntity(new URI("http://localhost:8080/login"),params
                        ,HttpHeaders.class).getStatusCode().is2xxSuccessful());
        params.clear();
    }

    @Given("^the passengers in the database are the following$")
    public void the_passengers_in_the_database_are_the_following(List<Passenger> passengers) {
        passengerList.addAll(passengers);
    }
    @Given("^them wallets in the database are the following$")
    public void them_wallets_in_the_database_are_the_following(List<Wallet> wallets) {
        for (int i=0;i<passengerList.size();i++) {
            passengerList.get(i).setWallet(wallets.get(i));
        }
        passengersRepo.deleteAll();
        passengersRepo.saveAll(passengerList);


    }
    @And("^the admin logged in with username (.*) and password (.*)$")
    public void the_admin_logged_in_with_username_and_password(String username, String password) throws URISyntaxException {
        params.put("username",username);
        params.put("password",password);
        token=testRestTemplate.
                postForEntity(new URI("http://localhost:8080/login"),params
                        ,HttpHeaders.class).getHeaders().get("Authorization").get(0);
        params.clear();
        headers.setBearerAuth(token);
    }
    @When("^the admin request a passenger with username (.*)$")
    public void the_admin_request_a_passenger_with_username(String username) throws URISyntaxException, JsonProcessingException {


        String response =(testRestTemplate.exchange("http://localhost:8080/admin/passenger/"+username,
                HttpMethod.GET,new HttpEntity<>(headers), String.class, params).getBody());
        actual.add(gson.fromJson(response, Passenger.class));
        headers.clear();
    }
    @Then("^the server response with the following passenger$")
    public void the_server_response_with_the_following(Passenger passenger) {
        expected.add(passenger);
    }
    @Then("this passenger wallet is")
    public void this_passenger_wallet_is(Wallet wallet) {
        Passenger temp= (Passenger) expected.get(0);
        temp.setWallet(wallet);
        Assertions.assertTrue(temp.equals(actual.get(0)));
        expected.clear();
        actual.clear();
    }










//    private void validateLines(){
//        Assertions.assertEquals(expectedLines.size(), actualLines.size());
//        IntStream.range(0,actualLines.size())
//                .forEach(
//                        index->validateLine(expectedLines.get(index),actualLines.get(index)));
//
//    }
//    private void validateLine(final Line expectedLine, final Line actualLine){
//        Assertions.assertTrue(expectedLine.equals(actualLine));
//    }



}
