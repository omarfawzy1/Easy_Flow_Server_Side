package com.easy_flow_server.service.owner;

import com.easy_flow_server.dto.model.AddOwnerModel;
import com.easy_flow_server.entity.Owner;
import com.easy_flow_server.entity.Turnstile;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repository.OwnerRepo;
import com.easy_flow_server.service.utils.ImageUtil;
import com.easy_flow_server.service.station_line.LineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepo ownerRepo;
    private final LineService lineService;

    public OwnerServiceImpl(OwnerRepo ownerRepo, LineService lineService) {
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
        if (owner == null) throw new BadRequestException("Owner not found");
        List<Object> result = new ArrayList<>();
        result.add(owner.getName());
        result.add(owner.getMail());
        result.add(owner.getBankAccount());
        result.add(lineService.getOwnerDetails(owner.getId()));
        if (owner.getImageData() == null)
            result.add(null);
        else
            result.add(ImageUtil.decompress(owner.getImageData().getImageData()));
        return result;

    }

    @Override
    public ResponseMessage deleteOwner(String username) {
        Owner temp = ownerRepo.findByName(username);
        if (temp == null) return new ResponseMessage("Owner not found", HttpStatus.NOT_FOUND);
        for (Turnstile turnstile : temp.getTurnstiles())
            turnstile.setOwner(null);

        ownerRepo.delete(temp);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public Owner getOwnerByUsername(String name) throws NotFoundException {
        Owner owner = ownerRepo.findByName(name);
        if (owner == null)
            throw new NotFoundException("Owner not found");
        return owner;
    }
}
