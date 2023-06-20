package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.PlanModel;
import com.example.easy_flow_backend.dto.Views.PlanView;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.owner_services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping("plans")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PASSENGER')")
    public List<PlanView> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("plans/{ownerName}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PASSENGER')")
    public List<PlanView> getAllOwnerPlans(@PathVariable String ownerName) {
        return planService.getAllOwnerPlans(ownerName);
    }

    @GetMapping("plan/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PASSENGER')")
    public PlanView getPlan(@PathVariable String planId) throws NotFoundException {
        return planService.getPlan(planId);
    }

    @PostMapping("plan")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseMessage addPlan(@RequestBody PlanModel plan) {
        return planService.addPlan(plan);
    }

}
