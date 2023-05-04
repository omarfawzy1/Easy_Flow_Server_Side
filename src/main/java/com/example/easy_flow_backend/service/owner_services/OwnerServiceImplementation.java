package com.example.easy_flow_backend.service.owner_services;
import com.example.easy_flow_backend.dto.Models.AddOwnerModel;

import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OwnerServiceImplementation implements OwnerService{
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private LineService lineService;

    @Override
    public boolean addOwner(AddOwnerModel addOwnerModel) throws BadRequestException {
        Owner owner =ownerRepo.findByName(addOwnerModel.getName());
        if(owner!=null) throw new BadRequestException("this username already used.");
        Owner tempOwner = new Owner(addOwnerModel.getName(), addOwnerModel.getMail(), addOwnerModel.getBankAccount());
        try {
            ownerRepo.save(tempOwner);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    @Override
    public List<Object> getOwnerDetails(String ownerName) throws BadRequestException {
        Owner owner =ownerRepo.findByName(ownerName);
        if(owner==null)throw new BadRequestException("there is no owner with this name.");
        List<Object> result=new ArrayList<>();
        result.add(owner.getName());
        result.add(owner.getMail());
        result.add(owner.getBankAccount());
        result.add(lineService.getOwnerDetails(owner.getId()));
        return result;

    }
}
