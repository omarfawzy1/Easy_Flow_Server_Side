package com.easy_flow_server.cucmber.glue;

import com.easy_flow_server.entity.Passenger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class PassengerGlue extends GlueConfig {

    @When("the passenger send register request with the following data")
    public void the_passenger_send_register_request(Passenger passenger) {

        params.put("firstName",passenger.getFirstName());
        params.put("lastName",passenger.getLastName());
        params.put("username",passenger.getUsername());
        params.put("email",passenger.getEmail());
        params.put("city",passenger.getCity());
        params.put("password",passenger.getPassword());
        params.put("phoneNumber",passenger.getPhoneNumber());
        params.put("gender","M");
        params.put("birthDay", "2023-02-11");
        actual.add(testRestTemplate.postForEntity("http://localhost:8080/Register",
                params, String.class).getStatusCode().value());
        params.clear();
        headers.clear();
    }
    @Then("^the server response with status (\\d+)$")
    public void the_server_response_with_status_200(int status) {
        Assertions.assertTrue(actual.get(0).equals(status));
    }




}
