package com.example.easy_flow_backend.service.owner_services;

import com.example.easy_flow_backend.dto.Models.PlanModel;
import com.example.easy_flow_backend.dto.Views.PlanView;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.List;

public interface PlanService {

    List<PlanView> getAllPlans();

    List<PlanView> getAllOwnerPlans(String ownerName);
    ResponseMessage addPlan(PlanModel plan);

    PlanView getPlan(String planId) throws NotFoundException;
}
