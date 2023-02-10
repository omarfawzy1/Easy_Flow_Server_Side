package com.example.easy_flow_backend.cucmber.glue;

import com.example.easy_flow_backend.entity.Administrator;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@ComponentScan({"com.example.easy_flow_backend"})
@SpringBootTest

public class AdminGlue {

    private RestTemplate testRestTemplate =new RestTemplate();
    Map<String, String> params = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();
    String token;

    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private UserRepositry userRepositry;
    private List<Line> expectedLines;
    private List<Line> actualLines;
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminGlue(){
    }
    @Given("the admin in the database are the following")
    public void the_admin_in_the_database_are_the_following(io.cucumber.datatable.DataTable dataTable) {
        userRepositry.deleteAll();
        Administrator admin;
        for(int i=0;i<dataTable.height();i++){
            admin=new Administrator(dataTable.cell(i,0),
                    dataTable.cell(i,1), passwordEncoder.encode(dataTable.cell(i,2)));
            userRepositry.save(admin);
        }
    }
    @When("^the admin try to log in with username (.*) and password (.*)")
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
    private void validateLines(){
        Assertions.assertEquals(expectedLines.size(), actualLines.size());
        IntStream.range(0,actualLines.size())
                .forEach(
                        index->validateLine(expectedLines.get(index),actualLines.get(index)));

    }
    private void validateLine(final Line expectedLine, final Line actualLine){
        Assertions.assertTrue(expectedLine.equals(actualLine));
    }



}
