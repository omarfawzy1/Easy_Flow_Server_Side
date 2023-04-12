package com.example.easy_flow_backend.cucmber;

import com.example.easy_flow_backend.EasyFlowBackendApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {EasyFlowBackendApplication.class, CucmberIT.class})
@CucumberOptions(plugin = {"pretty"}, tags = "",
        features = {
                "src/test/resources/features/Admin.feature",
                "src/test/resources/features/Passenger.feature"})
public class CucmberIT {

}
