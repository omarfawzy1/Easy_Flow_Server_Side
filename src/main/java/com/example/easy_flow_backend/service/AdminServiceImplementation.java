package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.repos.LineRepo;
import com.example.easy_flow_backend.repos.OwnerRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.view.AddLineView;
import com.example.easy_flow_backend.view.LineView;
import com.example.easy_flow_backend.view.PassagnerDetails;
import jakarta.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    private PassengersRepo passengerRepo;
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private OwnerRepo ownerRepo;

    @Override
    public List<LineView> getAllLines() {
        return lineRepo.findAllProjectedBy();
    }

    @Override
    public List<PassagnerDetails> getAllPassangers() {
        return passengerRepo.findAllProjectedBy();
    }

    @Override
    public Passenger getPassenger(String username) throws NotFoundException {

        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }

    @Override
    public ResponseEntity<String> deletePassenger(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        passengerRepo.deleteByUsernameIgnoreCase(username);
        return new ResponseEntity<>("Passenger deleted Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> passengerStatus(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");

        passenger.setActive(!passenger.isActive());
        passengerRepo.save(passenger);
        return new ResponseEntity<>("Accept", HttpStatus.OK);
    }

    @Override
    public LineView getLine(String id) throws NotFoundException {
        LineView line = lineRepo.findProjectedById(id);
        if (line == null)
            throw new NotFoundException("The Line Not Exist");

        return line;
    }

    @Override
    public ResponseEntity<String> deleteLine(String id) throws NotFoundException {
        if (!lineRepo.existsById(id))
            throw new NotFoundException("The Line Does Not Exist");
        lineRepo.deleteById(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addLine(AddLineView addLineView) {
//        if (lineRepo.existsByNameIgnoreCase(line.getName()))
//            return new ResponseEntity<>("The Line Already Exists", HttpStatus.NOT_FOUND);
        Optional<Owner> owner = ownerRepo.findById(addLineView.getOwnerId());

        if (!owner.isPresent()) {
            return new ResponseEntity<>("The Owner Not Exists", HttpStatus.BAD_REQUEST);
        }
        Line tmpLine = new Line(addLineView.getPrice(), owner.get());
        tmpLine.setName(addLineView.getLineName());
        try {
            lineRepo.save(tmpLine);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
