package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.service.graph.SingleLineGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("fest")
public class TestController {

    @Autowired
    private SingleLineGraphService graphService;
    @Autowired
    OwnerRepo ownerRepo;
    @Autowired
    LineRepo lineRepo;

    @GetMapping("weight/{start}/{end}")
    double getWeightTestForMovingTurnStile(
            @PathVariable("start") String station1, @PathVariable("end") String station2) {

        String lineId = lineRepo.findByName("M7").getId();
        String ownerId = ownerRepo.findByName("mwasalatmisr").getId();

        long curr = System.currentTimeMillis();
        String s = graphService.floydGraphWithStation(ownerId, lineId);
        System.out.println(s);
        System.out.printf("time to get graph= ");
        System.out.println(System.currentTimeMillis() - curr);
        curr = System.currentTimeMillis();

        double ans = graphService.getWeight(ownerId, lineId, station1, station2);
        System.out.printf("time to get weight= ");
        System.out.println(System.currentTimeMillis() - curr);

        System.out.println("Total time");
        return ans;
    }

}