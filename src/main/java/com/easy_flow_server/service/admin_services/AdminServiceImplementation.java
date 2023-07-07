package com.easy_flow_server.service.admin_services;

import com.easy_flow_server.dto.Models.*;
import com.easy_flow_server.dto.Views.*;
import com.easy_flow_server.entity.*;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.*;
import com.easy_flow_server.service.UserService;
import com.easy_flow_server.service.utils.ImageUtil;
import com.easy_flow_server.service.graph_services.GraphService;
import com.easy_flow_server.service.owner_services.OwnerService;
import com.easy_flow_server.service.passenger_services.PassengerService;
import com.easy_flow_server.service.payment_services.TicketService;
import com.easy_flow_server.service.station_line_services.LineService;
import com.easy_flow_server.service.station_line_services.StationService;
import com.easy_flow_server.service.tunstile_services.MovingTurnstileService;
import com.easy_flow_server.service.tunstile_services.StationeryTurnstileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdminServiceImplementation implements AdminService {

    private final OwnerRepo ownerRepo;
    private final LineService lineService;
    private final PassengerService passengerService;
    private final AnalysisService analysisService;
    private final OwnerService ownerService;
    private final StationeryTurnstileService stationeryTurnstileService;
    private final MovingTurnstileService movingTurnstileService;
    private final UserService userService;
    private final StationService stationService;
    private final TicketService ticketService;
    private final PrivilegeRepo privilegeRepo;
    private final PasswordEncoder passwordEncoder;
    private final StationaryTurnstileRepo stationaryTurnstileRepo;
    private final UserRepositry userRepositry;
    private final MovingTurnstileRepo movingTurnstileRepo;
    private final GraphService graphService;
    private final StationRepo stationRepo;
    private final PassengersRepo passengersRepo;

    public AdminServiceImplementation(AnalysisService analysisService, OwnerRepo ownerRepo, LineService lineService, PassengerService passengerService, UserRepositry userRepositry, StationaryTurnstileRepo stationaryTurnstileRepo, MovingTurnstileRepo movingTurnstileRepo, StationRepo stationRepo, OwnerService ownerService, StationeryTurnstileService stationeryTurnstileService, PassengersRepo passengersRepo, MovingTurnstileService movingTurnstileService, UserService userService, GraphService graphService, PasswordEncoder passwordEncoder, StationService stationService, TicketService ticketService, PrivilegeRepo privilegeRepo) {
        this.analysisService = analysisService;
        this.ownerRepo = ownerRepo;
        this.lineService = lineService;
        this.passengerService = passengerService;
        this.userRepositry = userRepositry;
        this.stationaryTurnstileRepo = stationaryTurnstileRepo;
        this.movingTurnstileRepo = movingTurnstileRepo;
        this.stationRepo = stationRepo;
        this.ownerService = ownerService;
        this.stationeryTurnstileService = stationeryTurnstileService;
        this.passengersRepo = passengersRepo;
        this.movingTurnstileService = movingTurnstileService;
        this.userService = userService;
        this.graphService = graphService;
        this.passwordEncoder = passwordEncoder;
        this.stationService = stationService;
        this.ticketService = ticketService;
        this.privilegeRepo = privilegeRepo;
    }

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
    public ResponseMessage deleteLine(String name) {
        boolean deleted;

        try {
            deleted = lineService.deleteLine(name);
        } catch (BadRequestException e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!deleted)
            return new ResponseMessage("The Line Does Not Exist", HttpStatus.NOT_FOUND);

        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addLine(AddLineModel addLineModel) {
        return lineService.addLine(addLineModel);
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
    public List<Object> getRevenue(TimePeriod timePeriod, String groupBy) throws BadRequestException {
        return analysisService.getRevenue(timePeriod, groupBy);
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


    @Override
    public GetGraphModel getGraph(String lineName) throws NotFoundException {
        Pair<List<Station>, List<Number>> orderedStation = graphService.getOrderedStationOfLine(lineName);
        return new GetGraphModel(orderedStation.getFirst(), orderedStation.getSecond());
    }

    @Override
    public ResponseMessage addOwner(AddOwnerModel addOwnerModel) {
        if (!ownerService.addOwner(addOwnerModel)) {
            return new ResponseMessage("The Owner Not Found", HttpStatus.NOT_FOUND);
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
    public ResponseMessage deleteStation(String name) {
        return stationService.deleteStation(name);
    }

    @Override
    public ResponseMessage flipUserActive(String username) {
        return userService.flipUserActive(username);
    }

    @Override
    public ResponseMessage deleteStationeryMachine(String username) {
        return stationeryTurnstileService.deletMachine(username);
    }

    @Override
    public ResponseMessage deleteMovingMachine(String username) {
        return movingTurnstileService.deletMachine(username);
    }

    @Override
    public ResponseMessage addTicket(TicketModel ticketModel) {
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
    public ResponseMessage setOwnerImage(String name, MultipartFile file) {
        Owner owner = ownerRepo.findByName(name);
        if (owner == null)
            return new ResponseMessage("this owner is not found", HttpStatus.BAD_REQUEST);
        try {
            owner.setImageData(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());
            ownerRepo.save(owner);
        } catch (Exception e) {
            return new ResponseMessage("image size is too big", HttpStatus.BAD_REQUEST);

        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addPrivilege(String privilege) {
        Privilege temp = privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if (temp != null)
            return new ResponseMessage("this privilege already exist", HttpStatus.CONFLICT);
        try {
            temp = new Privilege(privilege);
            privilegeRepo.save(temp);
        } catch (Exception e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage deletePrivilege(String privilege) {
        Privilege temp = privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if (temp == null)
            return new ResponseMessage("there is no privilege with this name", HttpStatus.NOT_FOUND);
        try {
            privilegeRepo.delete(temp);
        } catch (Exception e) {
            return new ResponseMessage("this privilege can't be deleted because it is used in plan or passenger", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public boolean addStationaryMachine(AddStationaryMachineModel addStationaryMachineModel) throws Exception {

        User user = userRepositry.findUserByUsername(addStationaryMachineModel.getUsername());
        if (user != null)
            throw new BadRequestException("Username already exists");

        Owner owner = ownerRepo.findByName(addStationaryMachineModel.getOwnerName());
        if (owner == null)
            throw new NotFoundException("Owner not Found");

        Station station = stationService.getStation(addStationaryMachineModel.getStationName());
        if (station == null)
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
        if (user != null)
            throw new BadRequestException("Username already exists");

        Owner owner = ownerRepo.findByName(addMovingMachineModel.getOwnerName());
        if (owner == null)
            throw new NotFoundException("Owner not found");

        Line line = lineService.getLineByName(addMovingMachineModel.getLineName());
        if (line == null)
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
        Passenger passenger = (Passenger) userRepositry.findUserByUsername(username);
        if (passenger == null)
            return new ResponseMessage("this passenger dose not exist", HttpStatus.BAD_REQUEST);
        Privilege privilege1 = privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if (privilege1 == null)
            return new ResponseMessage("this privilege dose not exist", HttpStatus.BAD_REQUEST);
        if (!passenger.getPrivileges().contains(privilege1))
            return new ResponseMessage("this passenger don't have this privilege", HttpStatus.BAD_REQUEST);
        try {
            privilege1.getPassengers().remove(passenger);
            passenger.getPrivileges().remove(privilege1);
            privilegeRepo.save(privilege1);
        } catch (Exception e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("privilege successfully removed", HttpStatus.OK);
    }

    @Override
    public ResponseMessage addGraph(GraphModel graphModel) {
        return graphService.addGraph(graphModel);
    }

    @Override
    public ResponseMessage updateMovingMachineLine(String username, String newLine) {
        MovingTurnstile movingTurnstile = movingTurnstileRepo.findUserByUsername(username);
        if (movingTurnstile == null) {
            return new ResponseMessage("Invalid username", HttpStatus.NOT_FOUND);
        }
        try {
            Line line = lineService.getLineByName(newLine);
            movingTurnstile.setLine(line);
            movingTurnstileRepo.save(movingTurnstile);
        } catch (NotFoundException e) {
            return new ResponseMessage("Invalid line Name", HttpStatus.NOT_FOUND);
        }
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage updateStationaryMachineStation(String username, String newStation) {
        StationaryTurnstile stationaryTurnstile = stationaryTurnstileRepo.findUserByUsername(username);
        if (stationaryTurnstile == null) {
            return new ResponseMessage("Invalid username", HttpStatus.NOT_FOUND);
        }
        Station station = stationService.getStation(newStation);
        if (station == null) {
            return new ResponseMessage("Invalid Station name", HttpStatus.NOT_FOUND);
        }
        stationaryTurnstile.setStation(station);
        stationaryTurnstileRepo.save(stationaryTurnstile);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public List<String> getPrivileges() {
        List<String> result = new ArrayList<>();
        for (Privilege p : privilegeRepo.findAll())
            result.add(p.getName());
        return result;
    }

    @Override
    public List<String> getPrivilegesForPassenger(String username) throws NotFoundException {
        Passenger passenger = passengerService.getPassenger(username);
        List<String> result = new ArrayList<>();
        for (Privilege p : passenger.getPrivileges())
            result.add(p.getName());
        return result;
    }

    @Override
    public List<StationView> getAllStation() {
        return stationRepo.findAllBy(StationView.class);
    }

    @Override
    public ResponseMessage addPrivilegesToPassenger(String username, String privilege) {
        Passenger passenger = passengersRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            return new ResponseMessage("passenger not found", HttpStatus.NOT_FOUND);
        Privilege privilege1 = privilegeRepo.findPrivilegeByNameIgnoreCase(privilege);
        if (privilege1 == null)
            return new ResponseMessage("privilege not found", HttpStatus.NOT_FOUND);
        if (passenger.getPrivileges().contains(privilege1))
            return new ResponseMessage("passenger already have this privilege", HttpStatus.ALREADY_REPORTED);
        passenger.addPrivilege(privilege1);
        passengersRepo.save(passenger);
        return new ResponseMessage("privilege added to the passenger", HttpStatus.OK);
    }

    @Override
    public List<Object> getTripPerHour(TimePeriod timePeriod) {
        return analysisService.getTripPerHour(timePeriod);
    }

    @Override
    public ResponseMessage deleteOwner(String username) {
        return ownerService.deleteOwner(username);
    }


}
