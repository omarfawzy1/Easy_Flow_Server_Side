package com.example.easy_flow_backend.service.owner_services;

import com.example.easy_flow_backend.dto.Models.AddOwnerModel;
import com.example.easy_flow_backend.error.BadRequestException;

import java.util.List;

public interface OwnerService {
    public boolean addOwner (AddOwnerModel addOwnerModel) throws BadRequestException;

    List<Object> getOwnerDetails(String ownerName)throws BadRequestException;
}
