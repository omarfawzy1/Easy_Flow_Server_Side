package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.Pair;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.service.TokenValidationService;
import com.example.easy_flow_backend.service.graph_services.GraphService;
import com.example.easy_flow_backend.service.graph_services.GraphWeightService;
import com.example.easy_flow_backend.service.notification.FirebaseNotificationService;
import com.example.easy_flow_backend.service.notification.PassengerNotification;
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

        return firebaseNotificationService.notifyPassenger("omar", new PassengerNotification("Hi Omar We have approved your VIP request", "Have a nice day")) ? "Sent Successfully" : "Did not Work Sorry";
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

    /*@GetMapping("getLineStations/{line_name}")
    public Pair<List<String>, List<Number>> no(@PathVariable("line_name") String lineName) throws NotFoundException {
        return graphService.getOrderedStationOfLine(lineName);
    }*/

    @Autowired
    private TokenValidationService tokenValidationService;
    @Autowired
    private PassengersRepo passengersRepo;

    @GetMapping("validateToken/{token}/{username}")
    public String testisvalid(@PathVariable("token") String token, @PathVariable("username") String username) {
        return tokenValidationService.validatePassengerToken(token, username) ? "Valid" : "notValid";
    }
}