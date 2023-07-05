package com.example.easy_flow_backend.controller;

import com.example.easy_flow_backend.dto.Models.*;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.service.admin_services.AdminService;
import com.example.easy_flow_backend.service.utils.Utility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("owners")
    public List<OwnerView> getALlOwners() {
        return adminService.getAllOwners();
    }

    @GetMapping("owners/{username}")
    public OwnerViewDetails getOwner(@PathVariable("username") String username) throws BadRequestException {
        return adminService.getOwner(username);
    }

    @GetMapping("passengers")
    public List<PassagnerBriefDetails> getAllPassengers() {
        return adminService.getAllPassangers();
    }

    @GetMapping("passenger/{username}")
    public PassagnerDetails getPassenger(@PathVariable String username) throws NotFoundException {
        return adminService.getPassengerDetails(username);
    }


    @GetMapping("line/graph/{line_name}")
    public GetGraphModel getGraph(@PathVariable("line_name") String lineName) throws NotFoundException {
        return adminService.getGraph(lineName);
    }
    //TODO
    @PostMapping("line/graph")
    public ResponseMessage addGraph(@Valid @RequestBody GraphModel graphModel)  {
        return adminService.addGraph(graphModel);
    }

    @DeleteMapping("passenger/{username}")
    public ResponseMessage deletePassenger(@PathVariable String username) {
        return adminService.deletePassenger(username);
    }

    @PutMapping("user/active/{username}")
    public ResponseMessage flipUserActive(@PathVariable String username) throws NotFoundException {
        return adminService.flipUserActive(username);
    }

    @GetMapping("lines")
    public List<LineView> getAllLines() {
        return adminService.getAllLines();
    }
    //TODO change name
    @GetMapping("lineDetails/{name}")
    public LiveWithStationsView getLineDetails(@PathVariable String name) {
        return adminService.getLineDetails(name);
    }

    @DeleteMapping("line/{name}")
    public ResponseMessage deleteLine(@PathVariable String name) throws NotFoundException {
        return adminService.deleteLine(name);
    }

    @DeleteMapping("station/{name}")
    public ResponseMessage deleteStation(@PathVariable String name) throws NotFoundException, BadRequestException {
        return adminService.deleteStation(name);
    }

    @PostMapping("line")
    public ResponseMessage addLine(@Valid @RequestBody AddLineModel addLineModel) throws BadRequestException {
        return adminService.addLine(addLineModel);
    }

    @GetMapping("passengers/count")
    public int getPassengersCount() {
        return adminService.getAllPassangersCount();
    }

    @GetMapping("passengers/count/{privilege}")
    public int getPassengersCountWithPrivilege(@PathVariable String privilege) {
        return adminService.getPassengersCountWithPrivilege(privilege);
    }

    @PostMapping("revenue")
    public long getRevenue(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenue(timePeriod);
    }
    @PostMapping("revenueAvg")
    public long getRevenueAvg(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getRevenueAvg(timePeriod);
    }

    @PostMapping("{passengerName}/revenueAvg")
    public long getRevenueAvgByPassenger(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String passengerName) {
        return adminService.getRevenueAvgByPassenger(timePeriod, passengerName);
    }

    @GetMapping("passenger/negativeBalanceCount")
    public int getNegativePassengerCount() {
        return adminService.getNegativePassengerCount();
    }

    @GetMapping("passenger/belowThresholdCount/{threshold}")
    public int getBelowThresholdCount(@PathVariable long threshold) {
        return adminService.getBelowThresholdCount(threshold);
    }

    @GetMapping("turnstile/status")
    public Object getTurnstilesStatus() {
        return adminService.getTurnstilesStatus();
    }

    @PostMapping("station/passengersCount/{stationName}")
    public Object getTripInStationCount(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String stationName) {
        return adminService.getTripInStationCount(timePeriod, stationName);
    }

    @PostMapping("line/tripAvgForBus/{lineName}/{timeUnit}")
    public Object getTripAvgByTimeUnitForBusLine(@Valid @RequestBody TimePeriod timePeriod,
                                                 @PathVariable String timeUnit,
                                                 @PathVariable String lineName) throws ParseException, BadRequestException {
        return adminService.getTripAvgByTimeUnitForBusLine(timePeriod, Utility.stringToMilleSecond(timeUnit), lineName);
    }

    //get the most (peekNumber) hours and them trip count for bus line
    @PostMapping("line/busPeek/{lineName}/{peekNumber}")
    public List<Object> getPeekHours(
            @Valid @RequestBody TimePeriod timePeriod,
            @PathVariable String lineName,
            @PathVariable int peekNumber) {
        return adminService.getPeekHours(timePeriod, lineName, peekNumber);
    }

    @GetMapping("transaction/count")
    public int getTransactionCount() {
        return adminService.getTransactionCount();
    }

    @GetMapping("trip/count")
    public int getTripCount() {
        return adminService.getTripCount();
    }

    @PostMapping("owner/add")
    public ResponseMessage addOwner(@Valid @RequestBody AddOwnerModel addOwnerModel) throws BadRequestException {
        return adminService.addOwner(addOwnerModel);
    }

    @DeleteMapping("owner/delete/{username}")
    public ResponseMessage deleteOwner(@PathVariable String username) throws BadRequestException {
        return adminService.deleteOwner(username);
    }
    @GetMapping("owner/getDetails/{ownerName}")
    public List<Object> getOwnerDetails(@PathVariable String ownerName) throws BadRequestException {
        return adminService.getOwnerDetails(ownerName);
    }
    @GetMapping("machine/stationery/{username}")
    public StationeryMachineView getStationMachine(@PathVariable String username) throws NotFoundException {
        return adminService.getStationMachine(username);
    }
    @GetMapping("machine/moving/{username}")
    public MovingMachineView getMovingMachine(@PathVariable String username) throws NotFoundException {
        return adminService.getMovingMachine(username);
    }
    @GetMapping("machine/moving")
    public List<MovingMachineView> getMovingMachines() {
        return adminService.getMovingMachines();
    }
    @GetMapping("machine/stationery")
    public List<StationeryMachineView> getStationMachines() {
        return adminService.getStationMachines();
    }
    @PostMapping("machine/moving")
    public ResponseMessage addMovingMachine(@RequestBody AddMovingMachineModel addMovingMachineModel) {
        try {
            adminService.addMovingMachineModel(addMovingMachineModel);
            return new ResponseMessage(HttpStatus.OK, "Machine added successfully");
        } catch (Exception e) {
            return new ResponseMessage(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @PostMapping("machine/stationary")
    public ResponseMessage addStationaryMachine(@RequestBody AddStationaryMachineModel addStationaryMachineModel) {
        try {
            adminService.addStationaryMachine(addStationaryMachineModel);
            return new ResponseMessage(HttpStatus.OK, "Machine added successfully");
        } catch (Exception e) {
            return new ResponseMessage(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @DeleteMapping("machine/stationery/{username}")
    public ResponseMessage deleteStationeryMachine(@PathVariable String username) throws NotFoundException {
        return adminService.deleteStationeryMachine(username);
    }

    @DeleteMapping("machine/moving/{username}")
    public ResponseMessage deleteMovingMachine(@PathVariable String username) throws NotFoundException {
        return adminService.deleteMovingMachine(username);
    }

    @PatchMapping("machine/moving")
    public ResponseMessage updateMovingMachineLine(@Valid @RequestBody UpdateMovingMachine updateMovingMachine) {
        return adminService.updateMovingMachineLine(updateMovingMachine.getUsername(), updateMovingMachine.getNewLine());
    }

    @PatchMapping("machine/stationary")
    public ResponseMessage updateStationaryMachineStation(@Valid @RequestBody UpateStationaryMachine stationaryMachine) {
        return adminService.updateStationaryMachineStation(stationaryMachine.getUsername(), stationaryMachine.getNewStation());
    }

    @PostMapping("tickets")
    public ResponseMessage addTicket(@RequestBody TicketModel ticketModel) throws NotFoundException {
        return adminService.addTicket(ticketModel);
    }
    @DeleteMapping("tickets/{id}")
    public void deleteTicket(@PathVariable String id) {
        adminService.deleteTicket(id);
    }

    @GetMapping("tickets/{id}")
    public TicketView getTicket(@PathVariable String id) {
        return adminService.getTicket(id);
    }

    @GetMapping("owners/tickets/{name}")
    public List<TicketView> getOwnerTickets(@PathVariable String name) {
        return adminService.getOwnerTickets(name);
    }
    //TODO
    @GetMapping("lines/tickets/{name}")
    public List<TicketView> getLineTickets(@PathVariable String name) {
        return adminService.getLineTickets(name);
    }

    @PostMapping("owners/setImage/{name}")
    public ResponseMessage setOwnerImage(@PathVariable String name, @RequestBody MultipartFile file) throws IOException {
        return adminService.setOwnerImage(name, file);
    }

    @PostMapping("privilege/add/{privilege}")
    public ResponseMessage addPrivilege(@PathVariable String privilege) {
        return adminService.addPrivilege(privilege);
    }

    @DeleteMapping("privilege/delete/{privilege}")
    public ResponseMessage deletePrivilege(@PathVariable String privilege) {
        return adminService.deletePrivilege(privilege);
    }
    @DeleteMapping("passengerPrivilege/delete/{username}/{privilege}")
    public ResponseMessage deletePassengerPrivilege(@PathVariable String username, @PathVariable String privilege) {
        return adminService.deletePassengerPrivilege(username, privilege);
    }
    @GetMapping("privilege")
    public List<String> getPrivileges() {
        return adminService.getPrivileges();
    }

    @GetMapping("privilege/{username}")
    public List<String> getPrivilegesForPassenger(@PathVariable String username) throws NotFoundException {
        return adminService.getPrivilegesForPassenger(username);
    }
    @GetMapping("stations")
    public List<StationView> getAllStation() {
        return adminService.getAllStation();
    }
    @PutMapping("passengerPrivilege/add/{username}/{privilege}")
    public ResponseMessage addPrivilegesToPassenger(@PathVariable String username, @PathVariable String privilege){
        return adminService.addPrivilegesToPassenger(username,privilege);
    }
    @PostMapping("trip/countPerHour")
    public List<Object> getTripPerHour(@Valid @RequestBody TimePeriod timePeriod) {
        return adminService.getTripPerHour(timePeriod);
    }
    @PostMapping("revenue/{groupBy}")
    public List<Object> getRevenue(@Valid @RequestBody TimePeriod timePeriod, @PathVariable String groupBy) throws BadRequestException {
        return adminService.getRevenue(timePeriod,groupBy);
    }

}
