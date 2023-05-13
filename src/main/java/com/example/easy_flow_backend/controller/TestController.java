package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.service.TokenValidationService;
import com.example.easy_flow_backend.service.graph_services.GraphService;
import com.example.easy_flow_backend.service.graph_services.GraphWeightService;
import com.example.easy_flow_backend.service.notification.FirebaseNotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("fest")
public class TestController {

    @Autowired
    OwnerRepo ownerRepo;
    @Autowired
    LineRepo lineRepo;

    @Autowired
    GraphWeightService graphWeightService;
    @Autowired
    FirebaseNotificationService firebaseNotificationService;

    //Frdous
    @GetMapping("weight/{start}/{end}")
    double getWeightTestForMovingTurnStile(
            @PathVariable("start") String station1, @PathVariable("end") String station2) throws NotFoundException {

        String lineId = lineRepo.findByName("M7").getId();


        long curr = System.currentTimeMillis();

        double ans = graphWeightService.getLineWeight(lineId, station1, station2);

        System.out.printf("time to get weight= ");
        System.out.println(System.currentTimeMillis() - curr);

        System.out.println("Total time");
        return ans;
    }

    //stationery
    @GetMapping("weight2/{start}/{end}")
    double getWeightTestForStationTurnStileIn(
            @PathVariable("start") String station1, @PathVariable("end") String station2) throws NotFoundException {

        String ownerId = ownerRepo.findByName("Cairo Government", Owner.class).getId();


        long curr = System.currentTimeMillis();

        double ans = graphWeightService.getOwnerWeight(ownerId, station1, station2);

        System.out.printf("time to get weight= ");

        System.out.println(System.currentTimeMillis() - curr);

        System.out.println("Total time");
        return ans;
    }

    @GetMapping("notify")
    public String notifyTest() {
        return firebaseNotificationService.sendAnyMessage();
    }

    @Autowired
    TokenValidationService rideModelValidationService;

    @GetMapping("test")
    public boolean tokenTest(@RequestBody @Valid RideModel rideModel) {
//        return rideModelValidationService.validatePassengerToken(rideModel.getToken(), rideModel.getGenerationTime());
        return true;
    }

    @Autowired
    private GraphService graphService;

    @GetMapping("getLineStations/{lineId}")
    public List<String> no(@PathVariable("lineId") String lineId) throws NotFoundException {
        return graphService.getOrderedStationOfLine(lineId);
    }

    @Autowired
    private TokenValidationService tokenValidationService;

    @GetMapping("validateToken/{token}/{username}")
    public String testisvalid(@PathVariable("token") String token, @PathVariable("username") String username) {
        return tokenValidationService.validatePassengerToken(token, username) ? "Valid" : "notValid";
    }
}