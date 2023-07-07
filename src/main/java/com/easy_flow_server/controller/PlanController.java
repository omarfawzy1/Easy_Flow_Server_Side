package com.easy_flow_server.controller;

import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.dto.Models.PlanModel;
import com.easy_flow_server.dto.Views.PlanView;
import com.easy_flow_server.service.owner_services.PlanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

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
