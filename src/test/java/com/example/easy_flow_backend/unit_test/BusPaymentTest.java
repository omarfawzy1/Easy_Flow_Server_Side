package com.example.easy_flow_backend.unit_test;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.MovingTurnstileService;
import com.example.easy_flow_backend.service.graph.SingleLineGraphService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BusPaymentTest {


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepositry userRepositry;

    @Autowired
    private PassengersRepo passengersRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private StationRepo stationRepo;
    @Autowired
    private TripRepo tripRepository;
    @Autowired
    private TicketRepo ticketRepository;
    @Autowired
    private GraphRepo graphRepo;
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;
    @Autowired
    private SingleLineGraphService singleLineGraphService;
    Passenger passenger;
    Owner owner;
    HashMap<Integer, MovingTurnstile> movingTurnstiles;
    String[] station_names;
    Line m7;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    void setPassenger(){
        passenger = new Passenger(new Wallet("CC"), "Omar", "Fawzy", "01251253311", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "omar", passwordEncoder.encode("omar"), "omar@gmail.com");
        userRepositry.save(passenger);
    }


    void setOwner(){
        owner = new Owner("Cairo Government2", "government@government.gov", "0000 0000 0000 0000");
        ownerRepo.save(owner);
    }
    /*
    * 7 stations
    *
    */
    void m7Init() throws ParseException {
        m7 = new Line("m7", 10, owner);
        station_names = new String[]{"Giza", "Cairo University", "Nasr el Deen", "Talbia", "Mariotia", "Mashal", "Medan Ryhmia"};

        // Add the stations to the line and save them
        ArrayList<Station> m7Stations = new ArrayList<>();
        for(String station_name : station_names){
            Station station = new Station(station_name);
            station.setTransportationType(TransportationType.BUS);
            m7Stations.add(station);
        }
        stationRepo.saveAll(m7Stations);
        m7Stations.forEach(m7::addStation);
        lineRepo.save(m7);
        // Graph
        Graph graph = new Graph();
        graph.setOwner(owner);
        graph.setLine(m7);
        graphRepo.save(graph);
        // Graph Edges
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(graph, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);

        // Turnstiles
        movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m7_" + i, passwordEncoder.encode("1234"));
            bus.setLine(m7);
            movingTurnstiles.put(i, bus);
            userRepositry.save(bus);
        }

    }
    @Autowired
    MovingTurnstileService movingTurnstileService;
    @Test
    public void allLineRideTest() throws ParseException, BadRequestException {
        setOwner();
        setPassenger();
        passenger.getWallet().setBalance(100.0);
        passengersRepo.save(passenger);
        // ride price
        m7Init();
        MovingTurnstile bus = movingTurnstiles.get(0);
        RideModel rideModel = new RideModel(passenger.getUsername(), bus.getId(),
                station_names[0],station_names[station_names.length-1],
                m7,owner,null
        );
        try {
            ResponseMessage m = movingTurnstileService.inRide(rideModel);
            assert m.getStatus().equals(HttpStatus.OK);
        }
        catch (BadRequestException ignored){
            assert false;
        }
    }

    @Test
    public void lineWeightingTest() throws ParseException, BadRequestException {
        setOwner();
        setPassenger();
        passenger.getWallet().setBalance(100.0);
        passengersRepo.save(passenger);
        // ride price
        m7Init();
        MovingTurnstile bus = movingTurnstiles.get(0);
        RideModel rideModel = new RideModel(passenger.getUsername(), bus.getId(),
                station_names[0],station_names[station_names.length-1],
                m7,owner,null
        );
        double weight = singleLineGraphService.getWeight(owner.getId(),m7.getId(),station_names[0], station_names[1]);
        System.out.println(weight);
        assertEquals(1.0, weight,0.1);
    }

    @Test
    public void lineWeightingTest2() throws ParseException, BadRequestException {
        setOwner();
        setPassenger();
        passenger.getWallet().setBalance(100.0);
        passengersRepo.save(passenger);
        // ride price
        m7Init();
        long start = System.currentTimeMillis();
        double weight = singleLineGraphService.getWeight(owner.getId(),m7.getId(),station_names[0], station_names[station_names.length - 1]);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(weight);
        assertEquals(station_names.length - 1.0, weight,0.1);
    }
}
