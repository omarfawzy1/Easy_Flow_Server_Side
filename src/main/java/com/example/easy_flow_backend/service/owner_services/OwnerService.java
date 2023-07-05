package com.example.easy_flow_backend.service.owner_services;

import com.example.easy_flow_backend.dto.Models.AddOwnerModel;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;

import java.util.List;

public interface OwnerService {
    public boolean addOwner(AddOwnerModel addOwnerModel);

    List<Object> getOwnerDetails(String ownerName) throws BadRequestException;

    ResponseMessage deleteOwner(String username) ;

    Owner getOwnerByUsername(String name) throws NotFoundException;
}
