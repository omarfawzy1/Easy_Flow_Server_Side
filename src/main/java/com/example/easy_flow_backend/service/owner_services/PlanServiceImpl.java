package com.example.easy_flow_backend.service.owner_services;

import com.example.easy_flow_backend.dto.Models.PlanModel;
import com.example.easy_flow_backend.dto.Views.PlanView;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.Plan;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.repos.PlanRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    PlanRepository planRepository;
    @Autowired
    OwnerRepo ownerRepo;

    @Override
    public List<PlanView> getAllPlans() {
        return planRepository.findAllBy(PlanView.class);
    }

    @Override
    public List<PlanView> getAllOwnerPlans(String ownerName) {
        return planRepository.findAllByOwnerName(ownerName, PlanView.class);
    }

    @Override
    public ResponseMessage addPlan(PlanModel plan) {

        Owner owner = ownerRepo.findByName(plan.getOwnerName());
        if (owner == null) {
            return new ResponseMessage("Owner Name is invalid", HttpStatus.BAD_REQUEST);
        }
        Plan newPlan = new Plan(plan.getPrivilege(), plan.getPrice(), plan.getDurationDays(), plan.getTrips(), plan.getName(), plan.getMaxCompanion(), owner, plan.getDiscountRate());
        try {
            planRepository.save(newPlan);
        } catch (Exception ex) {
            return new ResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Saved successfully", HttpStatus.OK);
    }

    @Override
    public PlanView getPlan(String planName, String ownerName) throws NotFoundException {
        PlanView planView = planRepository.findByNameAndOwnerName(planName, ownerName, PlanView.class);
        if (planView == null) throw new NotFoundException("Sorry, The Plan not found");
        return planView;
    }

    @Override
    public ResponseMessage deletePlan(String planName, String ownerName) {
        if (!planRepository.existsByNameAndOwnerName(planName, ownerName)) {
            return new ResponseMessage("Sorry, the Plan not available", HttpStatus.BAD_REQUEST);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, ownerName, Plan.class);
        planRepository.deleteById(plan.getId());
        return new ResponseMessage("Success", HttpStatus.OK);

    }

    @Override
    public ResponseMessage updatePlan(String planName, PlanModel planModel) {
        if (!planRepository.existsByNameAndOwnerName(planName, planModel.getOwnerName())) {
            return new ResponseMessage("Sorry, the Plan not available", HttpStatus.BAD_REQUEST);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, planModel.getOwnerName(), Plan.class);
        plan.setName(planModel.getName());
        plan.setDiscountRate(planModel.getDiscountRate());
        plan.setDurationDays(planModel.getDurationDays());
        plan.setMaxCompanion(planModel.getMaxCompanion());
        plan.setPrivilege(planModel.getPrivilege());
        plan.setNumberOfTrips(plan.getNumberOfTrips());
        plan.setPrice(planModel.getPrice());

        planRepository.save(plan);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
