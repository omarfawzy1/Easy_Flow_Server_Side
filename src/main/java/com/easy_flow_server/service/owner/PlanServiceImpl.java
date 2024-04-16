package com.easy_flow_server.service.owner;

import com.easy_flow_server.dto.model.PlanModel;
import com.easy_flow_server.dto.view.PlanView;
import com.easy_flow_server.entity.Owner;
import com.easy_flow_server.entity.Plan;
import com.easy_flow_server.entity.Privilege;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.OwnerRepo;
import com.easy_flow_server.repository.PlanRepo;
import com.easy_flow_server.repository.PrivilegeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepo planRepo;
    private final OwnerRepo ownerRepo;
    private final PrivilegeRepo privilegeRepo;

    public PlanServiceImpl(PlanRepo planRepo, OwnerRepo ownerRepo, PrivilegeRepo privilegeRepo) {
        this.planRepo = planRepo;
        this.ownerRepo = ownerRepo;
        this.privilegeRepo = privilegeRepo;
    }

    @Override
    public List<PlanView> getAllPlans() {
        return planRepo.findAllBy(PlanView.class);
    }

    @Override
    public List<PlanView> getAllOwnerPlans(String ownerName) {
        return planRepo.findAllByOwnerName(ownerName, PlanView.class);
    }

    @Override
    public ResponseMessage addPlan(PlanModel plan) {

        Owner owner = ownerRepo.findByName(plan.getOwnerName());
        if (owner == null) {
            return new ResponseMessage("Owner not found", HttpStatus.NOT_FOUND);
        }
        Privilege privilege = privilegeRepo.findPrivilegeByNameIgnoreCase(plan.getPrivilegeName());
        if (privilege == null) {
            return new ResponseMessage("Privilege not found", HttpStatus.NOT_FOUND);
        }
        Plan newPlan = new Plan(privilege, plan.getPrice(), plan.getDurationDays(), plan.getTrips(), plan.getName(), plan.getMaxCompanion(), owner, plan.getDiscountRate());
        try {
            planRepo.save(newPlan);
        } catch (Exception ex) {
            return new ResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public PlanView getPlan(String planName, String ownerName) throws NotFoundException {
        PlanView planView = planRepo.findByNameAndOwnerName(planName, ownerName, PlanView.class);
        if (planView == null) throw new NotFoundException("Plan not found");
        return planView;
    }

    @Override
    public ResponseMessage deletePlan(String planName, String ownerName) {
        if (!planRepo.existsByNameAndOwnerName(planName, ownerName)) {
            return new ResponseMessage("Plan not found", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepo.findByNameAndOwnerName(planName, ownerName, Plan.class);
        planRepo.deleteById(plan.getId());
        return new ResponseMessage("Success", HttpStatus.OK);

    }

    @Override
    public ResponseMessage updatePlan(String planName, PlanModel planModel) {
        if (!planRepo.existsByNameAndOwnerName(planName, planModel.getOwnerName())) {
            return new ResponseMessage("Plan not found", HttpStatus.NOT_FOUND);
        }
        Privilege privilege = privilegeRepo.findPrivilegeByNameIgnoreCase(planModel.getPrivilegeName());
        if (privilege == null) {
            return new ResponseMessage("Privilege not found", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepo.findByNameAndOwnerName(planName, planModel.getOwnerName(), Plan.class);
        plan.setName(planModel.getName());
        plan.setDiscountRate(planModel.getDiscountRate());
        plan.setDurationDays(planModel.getDurationDays());
        plan.setMaxCompanion(planModel.getMaxCompanion());
        plan.setPrivilege(privilege);
        plan.setNumberOfTrips(plan.getNumberOfTrips());
        plan.setPrice(planModel.getPrice());

        planRepo.save(plan);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

}
