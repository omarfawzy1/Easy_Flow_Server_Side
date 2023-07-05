package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.PlanModel;
import com.example.easy_flow_backend.dto.Views.PlanView;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.owner_services.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("plan/{owner_name}/{plan_name}")
    public PlanView getPlan(@PathVariable("owner_name") String owner_name, @PathVariable("plan_name") String plan_name) throws NotFoundException {
        return planService.getPlan(plan_name, owner_name);
    }

    @PostMapping("plan")
    public ResponseMessage addPlan(@Valid @RequestBody PlanModel plan) {
        return planService.addPlan(plan);
    }

    @DeleteMapping("plan/{owner_name}/{plan_name}")
    public ResponseMessage deletePlan(@PathVariable("owner_name") String owner_name, @PathVariable("plan_name") String plan_name) {
        return planService.deletePlan(plan_name, owner_name);
    }

    @PutMapping("plan/{plan_name}")
    public ResponseMessage updatePlan(@PathVariable String plan_name, @RequestBody PlanModel planModel) {
        return planService.updatePlan(plan_name, planModel);
    }
}
