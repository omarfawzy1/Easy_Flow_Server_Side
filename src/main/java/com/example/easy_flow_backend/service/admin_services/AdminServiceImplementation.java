package com.example.easy_flow_backend.service.admin_services;

import com.example.easy_flow_backend.dto.Models.*;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.UserService;
import com.example.easy_flow_backend.service.owner_services.OwnerService;
import com.example.easy_flow_backend.service.passenger_services.PassengerService;
import com.example.easy_flow_backend.service.payment_services.TicketService;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import com.example.easy_flow_backend.service.station_line_services.StationService;
import com.example.easy_flow_backend.service.tunstile_services.MovingTurnstileService;
import com.example.easy_flow_backend.service.tunstile_services.StationeryTurnstileService;
import com.example.easy_flow_backend.service.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private LineService lineService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private StationeryTurnstileService stationeryTurnstileService;
    @Autowired
    private MovingTurnstileService movingTurnstileService;
    @Autowired
    private UserService userService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PrivilegeRepo privilegeRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private MovingTurnstileRepo movingTurnstileRepo;

    @Override
    public List<LineView> getAllLines() {
        return lineService.getAllLines();
    }

    @Override
    public List<PassagnerBriefDetails> getAllPassangers() {
        return passengerService.getAllPassangers();
    }

    @Override
    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException {
        return passengerService.getPassengerDetails(username);
    }

    @Override
    public Passenger getPassenger(String username) throws NotFoundException {
        return passengerService.getPassenger(username);
    }

    @Override
    public ResponseMessage deletePassenger(String username) {
        return passengerService.deletePassenger(username);
    }


    @Override
    public ResponseMessage deleteLine(String name) throws NotFoundException {
        if (!lineService.deleteLine(name))
            throw new NotFoundException("The Line Does Not Exist");
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addLine(AddLineModel addLineModel) throws BadRequestException {
        if (!lineService.addLine(addLineModel)) {
            throw new BadRequestException("The Owner Not Exists");
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public int getAllPassangersCount() {
        return passengerService.getAllPassangersCount();
    }

    @Override
    public int getPassengersCountWithPrivilege(String privilege) {
        return passengerService.getPassengersCountWithPrivilege(privilege);
    }

    @Override
    public long getRevenue(TimePeriod timePeriod) {
        return analysisService.getRevenue(timePeriod);
    }

    @Override
    public long getRevenueAvg(TimePeriod timePeriod) {
        return analysisService.getRevenueAvg(timePeriod);
    }

    @Override
    public long getRevenueAvgByPassenger(TimePeriod timePeriod, String passengerName) {
        return analysisService.getRevenueAvgByPassenger(timePeriod, passengerName);
    }

    @Override
    public int getNegativePassengerCount() {
        return analysisService.getNegativePassengerCount();
    }

    @Override
    public int getBelowThresholdCount(long threshold) {
        return analysisService.getBelowThresholdCount(threshold);
    }

    @Override
    public Object getTurnstilesStatus() {
        return analysisService.getTurnstilesStatus();
    }

    @Override
    public int getTripInStationCount(TimePeriod timePeriod, String stationName) {
        return analysisService.getTripInStationCount(timePeriod, stationName);
    }

    @Override
    public long getTripAvgByTimeUnitForBusLine(TimePeriod timePeriod, Long timeUnit, String lineName) throws BadRequestException {
        return analysisService.getTripAvgByTimeUnitForBusLine(timePeriod, timeUnit, lineName);
    }

    @Override
    public List<Object> getPeekHours(TimePeriod timePeriod, String lineName, int peekNumber) {
        return analysisService.getPeekHours(timePeriod, lineName, peekNumber);
    }

    @Override
    public int getTransactionCount() {
        return analysisService.getTransactionCount();
    }

    @Override
    public int getTripCount() {
        return analysisService.getTripCount();
    }

    @Override
    public List<OwnerView> getAllOwners() {
        return ownerRepo.findALLBy(OwnerView.class);
    }

    //TODO
    /*@Override
    public boolean addGraph(GraphModel graphModel) {
        // TODO Graph Model Validation
        if (!graphService.addGraph(graphModel.getGraph())) {
            return false;
        }
        return graphEdgeService.addEdges(graphModel.getGraphEdgeList());
    }
*/
    @Override
    public List<GraphEdge> getGraph(String ownerId, String lineId) throws NotFoundException {
        return new ArrayList<>(lineService.getLineById(lineId).getGraphEdges());
//        return graphEdgeService.getEdges(graphService.getLineGraph(ownerId, lineId));
    }

    @Override
    public ResponseMessage addOwner(AddOwnerModel addOwnerModel) throws BadRequestException {
        if (!ownerService.addOwner(addOwnerModel)) {
            throw new BadRequestException("The Owner Not Exists");
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public List<Object> getOwnerDetails(String ownerName) throws BadRequestException {
        return ownerService.getOwnerDetails(ownerName);

    }

    @Override
    public LiveWithStationsView getLineDetails(String name) {
        return lineService.getLineDetails(name);
    }


    @Override
    public OwnerViewDetails getOwner(String username) throws BadRequestException {
        OwnerViewDetails owner = ownerRepo.findByName(username, OwnerViewDetails.class);
        if (owner == null)
            throw new BadRequestException("The owner Not exist");
        return owner;
    }

    @Override
    public List<Turnstile> getAllMachines() {
        List<Turnstile> machines = new ArrayList<>(movingTurnstileService.getAllMachines());
        machines.addAll(stationeryTurnstileService.getAllMachines());
        return machines;
    }

    @Override
    public MovingMachineView getMovingMachine(String username) throws NotFoundException {
        return movingTurnstileService.findProjectedByUsername(username);
    }

    @Override
    public StationeryMachineView getStationMachine(String username) throws NotFoundException {

        return stationeryTurnstileService.findProjectedByUsername(username);
    }

    @Override
    public List<MovingMachineView> getMovingMachines() {
        return movingTurnstileService.getMachines();
    }

    @Override
    public List<StationeryMachineView> getStationMachines() {
        return stationeryTurnstileService.getMachines();
    }

    @Override
    public ResponseMessage deleteStation(String name) throws BadRequestException {
        return stationService.deleteStation(name);
    }

    @Override
    public ResponseMessage flipUserActive(String username) throws NotFoundException {
        return userService.flipUserActive(username);
    }

    @Override
    public ResponseMessage deleteStationeryMachine(String username) throws NotFoundException {
        return stationeryTurnstileService.deletMachine(username);
    }

    @Override
    public ResponseMessage deleteMovingMachine(String username) throws NotFoundException {
        return movingTurnstileService.deletMachine(username);
    }

    @Override
    public ResponseMessage addTicket(TicketModel ticketModel) throws NotFoundException {
        return ticketService.addTicket(ticketModel);
    }

    @Override
    public void deleteTicket(String id) {
        ticketService.deleteTicket(id);
    }

    @Override
    public TicketView getTicket(String id) {
        return ticketService.getTicket(id);
    }

    @Override
    public List<TicketView> getOwnerTickets(String name) {
        return ticketService.getOwnerTickets(name);
    }

    @Override
    public List<TicketView> getLineTickets(String name) {
        return ticketService.getLineTickets(name);
    }

    @Override
    public ResponseMessage setOwnerImage(String name, MultipartFile file) throws IOException {
        Owner owner=ownerRepo.findByName(name);
        if(owner == null)
            return new ResponseMessage("this owner is not found",HttpStatus.BAD_REQUEST);
        try{
            owner.setImageData(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());
            ownerRepo.save(owner);
        }
        catch (Exception e){
            return new ResponseMessage("image size is too big",HttpStatus.BAD_REQUEST);

        }
        return new ResponseMessage("Success",HttpStatus.OK);
    }

    @Override
    public ResponseMessage addPrivilege(String privilege) {
        Privilege temp=privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if(temp!=null)
            return new ResponseMessage("this privilege already exist",HttpStatus.CONFLICT);
        try{
            temp=new Privilege(privilege);
            privilegeRepo.save(temp);
        }
        catch (Exception e){
            return new ResponseMessage( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage( "Success",HttpStatus.OK);
    }

    @Override
    public ResponseMessage deletePrivilege(String privilege) {
        Privilege temp=privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if(temp==null)
            return new ResponseMessage("there is no privilege with this name",HttpStatus.BAD_REQUEST);
        try{
            privilegeRepo.delete(temp);
        }
        catch (Exception e){
            return new ResponseMessage( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage( "Success",HttpStatus.OK);
    }

    @Override
    public boolean addStationaryMachine(AddStationaryMachineModel addStationaryMachineModel) throws Exception {

        User user = userRepositry.findUserByUsername(addStationaryMachineModel.getUsername());
        if(user != null)
            throw new BadRequestException("Username already exists");

        Owner owner = ownerRepo.findByName(addStationaryMachineModel.getOwnerName());
        if(owner == null)
            throw new NotFoundException("Owner not Found");

        Station station = stationService.getStation(addStationaryMachineModel.getStationName());
        if(station == null)
            throw new NotFoundException("Station not Found");
        StationaryTurnstile machine = new StationaryTurnstile(addStationaryMachineModel.getUsername(),
                passwordEncoder.encode(addStationaryMachineModel.getPassword()),
                owner);

        machine.setActive(true);
        machine.setStation(station);
        stationaryTurnstileRepo.save(machine);
        return true;
    }

    @Override
    public boolean addMovingMachineModel(AddMovingMachineModel addMovingMachineModel) throws BadRequestException, NotFoundException {


        User user = userRepositry.findUserByUsername(addMovingMachineModel.getUsername());
        if(user != null)
            throw new BadRequestException("Username already exists");

        Owner owner = ownerRepo.findByName(addMovingMachineModel.getOwnerName());
        if(owner == null)
            throw new NotFoundException("Owner not found");

        Line line = lineService.getLineByName(addMovingMachineModel.getLineName());
        if(line == null)
            throw new NotFoundException("Line not found");
        MovingTurnstile machine = new MovingTurnstile(addMovingMachineModel.getUsername(),
                passwordEncoder.encode(addMovingMachineModel.getPassword()),
                owner
                );
        machine.setActive(true);
        machine.setLine(line);
        movingTurnstileRepo.save(machine);
        return true;
    }

    @Override
    public ResponseMessage deletePassengerPrivilege(String username, String privilege) {
        Passenger passenger= (Passenger) userRepositry.findUserByUsername(username);
        if(passenger==null)
            return new ResponseMessage("this passenger dose not exist",HttpStatus.BAD_REQUEST);
        Privilege privilege1=  privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if(privilege1==null)
            return new ResponseMessage("this privilege dose not exist",HttpStatus.BAD_REQUEST);
        if(!passenger.getPrivileges().contains(privilege1))
            return new ResponseMessage("this passenger don't have this privilege",HttpStatus.BAD_REQUEST);
        try{
            privilege1.getPassengers().remove(passenger);
            passenger.getPrivileges().remove(privilege1);
            privilegeRepo.save(privilege1);
        }
        catch (Exception e){
            return new ResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("privilege successfully removed",HttpStatus.OK);
    }

    @Override
    public ResponseMessage deleteOwner(String username) throws BadRequestException {
        return ownerService.deleteOwner(username);
    }


}
