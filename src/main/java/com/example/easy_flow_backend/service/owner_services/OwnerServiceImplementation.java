package com.example.easy_flow_backend.service.owner_services;

import com.example.easy_flow_backend.dto.Models.AddOwnerModel;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.Turnstile;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import com.example.easy_flow_backend.service.utils.ImageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OwnerServiceImplementation implements OwnerService {
    private final OwnerRepo ownerRepo;
    private final LineService lineService;

    public OwnerServiceImplementation(OwnerRepo ownerRepo, LineService lineService) {
        this.ownerRepo = ownerRepo;
        this.lineService = lineService;
    }

    @Override
    public boolean addOwner(AddOwnerModel addOwnerModel) {
        Owner owner = ownerRepo.findByName(addOwnerModel.getName());
        if (owner != null) return false;
        Owner tempOwner = new Owner(addOwnerModel.getName(), addOwnerModel.getMail(), addOwnerModel.getBankAccount());
        try {
            ownerRepo.save(tempOwner);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Object> getOwnerDetails(String ownerName) throws BadRequestException {
        Owner owner = ownerRepo.findByName(ownerName);
        if (owner == null) throw new BadRequestException("there is no owner with this name.");
        List<Object> result = new ArrayList<>();
        result.add(owner.getName());
        result.add(owner.getMail());
        result.add(owner.getBankAccount());
        result.add(lineService.getOwnerDetails(owner.getId()));
        if (owner.getImageData() == null)
            result.add(null);
        else
            result.add(ImageUtil.decompressImage(owner.getImageData().getImageData()));
        return result;

    }

    @Override
    public ResponseMessage deleteOwner(String username) {
        Owner temp = ownerRepo.findByName(username);
        if (temp == null) return new ResponseMessage("there is no owner with this name.", HttpStatus.NOT_FOUND);
        for (Turnstile turnstile : temp.getTurnstiles())
            turnstile.setOwner(null);

        ownerRepo.delete(temp);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public Owner getOwnerByUsername(String name) throws NotFoundException {
        Owner owner = ownerRepo.findByName(name);
        if (owner == null)
            throw new NotFoundException("Invalid Owner name.");
        return owner;
    }
}
