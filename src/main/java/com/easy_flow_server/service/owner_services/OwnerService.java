package com.easy_flow_server.service.owner_services;

import com.easy_flow_server.dto.Models.AddOwnerModel;
import com.easy_flow_server.entity.Owner;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;

import java.util.List;

public interface OwnerService {
    public boolean addOwner(AddOwnerModel addOwnerModel);

    List<Object> getOwnerDetails(String ownerName) throws BadRequestException;

    ResponseMessage deleteOwner(String username) ;

    Owner getOwnerByUsername(String name) throws NotFoundException;
}
