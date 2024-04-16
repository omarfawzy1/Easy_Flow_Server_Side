package com.easy_flow_server.cucmber;

import com.easy_flow_server.EasyFlowApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {EasyFlowApplication.class, CucmberIT.class})
@CucumberOptions(plugin = {"pretty"}, tags = "",
        features = {
                "src/test/resources/features/Admin.feature",
                "src/test/resources/features/Passenger.feature"})
public class CucmberIT {
}
