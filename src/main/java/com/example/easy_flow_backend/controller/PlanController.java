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
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PASSENGER')")
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping("plans")
    public List<PlanView> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("plans/{ownerName}")
    public List<PlanView> getAllOwnerPlans(@PathVariable String ownerName) {
        return planService.getAllOwnerPlans(ownerName);
    }

    @GetMapping("plan/{planId}")
    public PlanView getPlan(@PathVariable String planId) throws NotFoundException {
        return planService.getPlan(planId);
    }

    @PostMapping("plan")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseMessage addPlan(@RequestBody PlanModel plan) {
        return planService.addPlan(plan);
    }

    @DeleteMapping("plan/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseMessage deletePlan(@PathVariable String planId) {
        return planService.deletePlan(planId);
    }

    @PutMapping("plan/{planId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseMessage deletePlan(@PathVariable String planId, @RequestBody PlanModel planModel) {
        return planService.updatePlan(planId, planModel);
    }
}
