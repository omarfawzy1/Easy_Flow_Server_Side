package com.easy_flow_server.cucmber.glue;

import com.easy_flow_server.entity.Administrator;
import com.easy_flow_server.entity.Passenger;
import com.easy_flow_server.entity.Wallet;
import com.example.easy_flow_backend.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@ComponentScan({"com.example.easy_flow_backend"})
@SpringBootTest

public class AdminGlue extends GlueConfig {

    public AdminGlue(){
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
        tripRepo.deleteAll();
        passengersRepo.deleteAll();
        passengersRepo.saveAll(passengerList);
        passengerList.clear();
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
        actual.add(objectMapper.readValue(response,Passenger.class));
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

    @When("the admin request to get all the passengers")
    public void the_admin_request_to_get_all_the_passengers() throws JsonProcessingException {
        String response =(testRestTemplate.exchange("http://localhost:8080/admin/passengers",
                HttpMethod.GET,new HttpEntity<>(headers), String.class, params).getBody());

        //Passenger[] passengerList = gson.fromJson(response, Passenger[].class);
        Passenger[] passengerList=objectMapper.readValue(response,Passenger[].class);
        actual.addAll(Arrays.stream(passengerList).toList());
        headers.clear();
    }
    @Then("the server response with the following passengers")
    public void the_server_response_with_the_following_passengers(List<Passenger> passengers) {
        expected.addAll(passengers);
    }
    @Then("this passengers wallets are")
    public void this_passengers_wallets_are(List<Wallet> wallets) {
        List<Passenger> temp=new ArrayList<>();
        for (int i=0; i<expected.size(); i++){
            temp.add ((Passenger) expected.get(i));
            temp.get(i).setWallet(wallets.get(i));
        }
        match(actual, Collections.singletonList(temp));
        expected.clear();
        actual.clear();
    }


}
