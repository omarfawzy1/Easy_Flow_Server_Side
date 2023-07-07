package com.easy_flow_server.service.owner_services;

import com.easy_flow_server.dto.Models.PlanModel;
import com.easy_flow_server.dto.Views.PlanView;
import com.easy_flow_server.entity.Owner;
import com.easy_flow_server.entity.Plan;
import com.easy_flow_server.entity.Privilege;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.OwnerRepo;
import com.easy_flow_server.repos.PlanRepository;
import com.easy_flow_server.repos.PrivilegeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final OwnerRepo ownerRepo;
    private final PrivilegeRepo privilegeRepo;

    public PlanServiceImpl(PlanRepository planRepository, OwnerRepo ownerRepo, PrivilegeRepo privilegeRepo) {
        this.planRepository = planRepository;
        this.ownerRepo = ownerRepo;
        this.privilegeRepo = privilegeRepo;
    }

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
            return new ResponseMessage("Owner name is invalid", HttpStatus.NOT_FOUND);
        }
        Privilege privilege = privilegeRepo.findPrivilegeByNameIgnoreCase(plan.getPrivilegeName());
        if (privilege == null) {
            return new ResponseMessage("Privilege Name is invalid", HttpStatus.NOT_FOUND);
        }
        Plan newPlan = new Plan(privilege, plan.getPrice(), plan.getDurationDays(), plan.getTrips(), plan.getName(), plan.getMaxCompanion(), owner, plan.getDiscountRate());
        try {
            planRepository.save(newPlan);
        } catch (Exception ex) {
            return new ResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseMessage("Saved successfully", HttpStatus.OK);
    }

    @Override
    public PlanView getPlan(String planName, String ownerName) throws NotFoundException {
        PlanView planView = planRepository.findByNameAndOwnerName(planName, ownerName, PlanView.class);
        if (planView == null) throw new NotFoundException("Sorry, The plan not found");
        return planView;
    }

    @Override
    public ResponseMessage deletePlan(String planName, String ownerName) {
        if (!planRepository.existsByNameAndOwnerName(planName, ownerName)) {
            return new ResponseMessage("Sorry, The plan not available", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, ownerName, Plan.class);
        planRepository.deleteById(plan.getId());
        return new ResponseMessage("Success", HttpStatus.OK);

    }

    @Override
    public ResponseMessage updatePlan(String planName, PlanModel planModel) {
        if (!planRepository.existsByNameAndOwnerName(planName, planModel.getOwnerName())) {
            return new ResponseMessage("Sorry, The Plan not available", HttpStatus.NOT_FOUND);
        }
        Privilege privilege = privilegeRepo.findPrivilegeByNameIgnoreCase(planModel.getPrivilegeName());
        if (privilege == null) {
            return new ResponseMessage("Privilege name is invalid", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, planModel.getOwnerName(), Plan.class);
        plan.setName(planModel.getName());
        plan.setDiscountRate(planModel.getDiscountRate());
        plan.setDurationDays(planModel.getDurationDays());
        plan.setMaxCompanion(planModel.getMaxCompanion());
        plan.setPrivilege(privilege);
        plan.setNumberOfTrips(plan.getNumberOfTrips());
        plan.setPrice(planModel.getPrice());

        planRepository.save(plan);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
