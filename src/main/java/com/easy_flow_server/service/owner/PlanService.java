package com.easy_flow_server.service.owner;

import com.easy_flow_server.dto.model.PlanModel;
import com.easy_flow_server.dto.view.PlanView;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface PlanService {

    List<PlanView> getAllPlans();

    List<PlanView> getAllOwnerPlans(String ownerName);

    ResponseMessage addPlan(PlanModel plan);

    PlanView getPlan(String planName, String ownerName) throws NotFoundException;

    ResponseMessage deletePlan(String planName, String ownerName);

    ResponseMessage updatePlan(String planName,PlanModel plan);
}
