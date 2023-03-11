package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private PassengersRepo passengersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
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
    private GraphRepo graphRepo;
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Override
    public void run(String... args) {
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<User> users = usersInit();

        //owner
        Owner owner1 = new Owner("ehab", "ehab@mail.com", "1148 1124 2247 2247");
        ownerRepo.save(owner1);

        //line and station
        Line line1 = new Line("line1", 10.0F, owner1);
        Line line2 = new Line("line2", 31.0F, owner1);
        Station station1 = new Station("first station");
        Station station2 = new Station("second station");
        userRepositry.saveAll(users);
        lines.add(line1);
        lines.add(line2);
        lineRepo.saveAll(lines);
        stationRepo.save(station2);
        stationRepo.save(station1);
        line1.addStation(station1);
        line2.addStation(station1);
        line1.addStation(station2);
        lineRepo.saveAll(lines);

        line2Init();
        m7Init();
        // test database
        System.out.println(userRepositry.findUserByUsername("haridy").getUsername());
    }

    ArrayList<User> usersInit() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Administrator("haridy", "haridy", passwordEncoder.encode("haridy")));
        users.add(new Passenger(new Wallet("CC"), "Omar", "Fawzy", "01251253311", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "omar", passwordEncoder.encode("omar"), "omar@gmail.com"));
        users.add(new Passenger(new Wallet("CC", 9787654.68), "ALy", "Khaled", "01256156165", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "aly", passwordEncoder.encode("omar"), "aly@gmail.com"));
        users.add(new Passenger(new Wallet("CC"), "Waled", "Yahia", "01254556464", "Regular", "Giza", Gender.M, new Date(System.currentTimeMillis()), "waled", passwordEncoder.encode("omar"), "waled@gmail.com"));
        users.add(new Passenger(new Wallet("CC"), "Mona", "Mahmoud", "12311561655", "Regular", "Cairo", Gender.F, new Date(System.currentTimeMillis()), "mona", passwordEncoder.encode("omar"), "mona@gmail.com"));
        return users;
    }

    @SneakyThrows
    void line2Init() {
        // Create and save the owner of the line
        Owner cairoGovernment = new Owner("Cairo Government", "government@government.gov", "0000 0000 0000 0000");
        ownerRepo.save(cairoGovernment);

        // Create the line and its stations
        Line line2 = new Line("Line 2", 10, cairoGovernment);
        Station elMonibStation = new Station("El Monib");
        Station sakiatMekkiStation = new Station("Sakiat Mekki");
        Station ommElMisryeenStation = new Station("Omm el Misryeen");
        Station gizaStation = new Station("Giza");
        Station faysalStation = new Station("Faysal");
        Station cairoUniversityStation = new Station("Cairo University");
        Station elBohoosStation = new Station("El Bohoos");
        Station dokkiStation = new Station("Dokki");
        Station operaStation = new Station("Opera");

        // Add the stations to the line and save them
        ArrayList<Station> line2Stations = new ArrayList<>();
        line2Stations.add(elMonibStation);
        line2Stations.add(sakiatMekkiStation);
        line2Stations.add(ommElMisryeenStation);
        line2Stations.add(gizaStation);
        line2Stations.add(faysalStation);
        line2Stations.add(cairoUniversityStation);
        line2Stations.add(elBohoosStation);
        line2Stations.add(dokkiStation);
        line2Stations.add(operaStation);
        line2Stations.forEach(station -> station.setTransportationType(TransportationType.METRO));

        stationRepo.saveAll(line2Stations);
        line2Stations.forEach(line2::addStation);
        lineRepo.save(line2);

        // Create and save stationary turnstiles for each station
        HashMap<String, StationaryTurnstile> stationaryTurnstiles = new HashMap<>();
        for (Station station : line2Stations) {
            String turnstileName = String.format("%s_%d_in", station.getStationName().toLowerCase().replaceAll(" ", ""), userRepositry.count());
            String password = passwordEncoder.encode("1234");
            StationaryTurnstile stationaryTurnstile = new StationaryTurnstile(turnstileName, password);
            stationaryTurnstile.setStation(station);
            stationaryTurnstiles.put(station.getStationName(), stationaryTurnstile);
            userRepositry.save(stationaryTurnstile);
        }

        // Create some passengers
        Passenger omar = passengersRepo.findUserByUsername("omar");
        Passenger waled = passengersRepo.findUserByUsername("waled");
        Passenger aly = passengersRepo.findUserByUsername("aly");
        Passenger mona = passengersRepo.findUserByUsername("mona");

        // Create some trips
        ArrayList<Trip> trips = new ArrayList<>();
        trips.add(new Trip(
                omar,
                stationaryTurnstiles.get(gizaStation.getStationName()),
                stationaryTurnstiles.get(elMonibStation.getStationName()),
                simpleDateFormat.parse("2023-11-01 09:54:31"),
                simpleDateFormat.parse("2023-11-01 11:40:22"),
                TransportationType.METRO,
                40f,
                Status.Closed,
                gizaStation.getStationName(),
                elMonibStation.getStationName())
        );

        // trip for Aly
        trips.add(new Trip(
                aly,
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                stationaryTurnstiles.get(dokkiStation.getStationName()),
                simpleDateFormat.parse("2023-11-02 08:30:00"),
                simpleDateFormat.parse("2023-11-02 09:15:00"),
                TransportationType.METRO,
                20f,
                Status.Closed,
                sakiatMekkiStation.getStationName(),
                dokkiStation.getStationName())
        );

        // trip for Waled
        trips.add(new Trip(
                waled,
                stationaryTurnstiles.get(elBohoosStation.getStationName()),
                stationaryTurnstiles.get(faysalStation.getStationName()),
                simpleDateFormat.parse("2023-11-03 12:45:00"),
                simpleDateFormat.parse("2023-11-03 13:20:00"),
                TransportationType.METRO,
                10f,
                Status.Closed,
                elBohoosStation.getStationName(),
                faysalStation.getStationName())
        );

        // trip for Mona
        trips.add(new Trip(
                mona,
                stationaryTurnstiles.get(gizaStation.getStationName()),
                stationaryTurnstiles.get(ommElMisryeenStation.getStationName()),
                simpleDateFormat.parse("2023-11-04 17:30:00"),
                simpleDateFormat.parse("2023-11-04 18:10:00"),
                TransportationType.METRO,
                15f,
                Status.Closed,
                gizaStation.getStationName(),
                ommElMisryeenStation.getStationName())
        );

        // trip for Omar
        trips.add(new Trip(
                omar,
                stationaryTurnstiles.get(operaStation.getStationName()),
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                simpleDateFormat.parse("2023-11-05 10:00:00"),
                simpleDateFormat.parse("2023-11-05 10:45:00"),
                TransportationType.METRO,
                20f,
                Status.Closed,
                operaStation.getStationName(),
                sakiatMekkiStation.getStationName())
        );


        trips.add(new Trip(
                waled,
                stationaryTurnstiles.get(cairoUniversityStation.getStationName()),
                stationaryTurnstiles.get(elBohoosStation.getStationName()),
                simpleDateFormat.parse("2023-03-10 08:30:00"),
                simpleDateFormat.parse("2023-03-10 09:00:00"),
                TransportationType.METRO,
                10f,
                Status.Closed,
                cairoUniversityStation.getStationName(),
                elBohoosStation.getStationName())
        );

        trips.add(new Trip(
                aly,
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                stationaryTurnstiles.get(operaStation.getStationName()),
                simpleDateFormat.parse("2023-04-02 17:15:00"),
                simpleDateFormat.parse("2023-04-02 18:05:00"),
                TransportationType.METRO,
                7f,
                Status.Closed,
                sakiatMekkiStation.getStationName(),
                operaStation.getStationName())
        );

        trips.add(new Trip(
                mona,
                stationaryTurnstiles.get(ommElMisryeenStation.getStationName()),
                stationaryTurnstiles.get(dokkiStation.getStationName()),
                simpleDateFormat.parse("2023-05-21 13:40:00"),
                simpleDateFormat.parse("2023-05-21 14:15:00"),
                TransportationType.METRO,
                5f,
                Status.Closed,
                ommElMisryeenStation.getStationName(),
                dokkiStation.getStationName())
        );
        tripRepository.saveAll(trips);
    }

    @SneakyThrows
    void m7Init() {
        Owner mwasalatmisr = new Owner("mwasalatmisr", "mwasalatmisr@government.gov", "0000 0000 0000 0000");
        ownerRepo.save(mwasalatmisr);

        Line m7 = new Line("m7", 10, mwasalatmisr);
        Station gizaStation = new Station("Giza");
        Station cairoUniversityStation = new Station("Cairo University");
        Station nasrElDeenStation = new Station("Nasr el Deen");
        Station talbiaStation = new Station("Talbia");
        Station mariotiaStation = new Station("Mariotia");
        Station mashalStation = new Station("Mashal");
        Station medanRyhmiaStation = new Station("Medan Ryhmia");


        // Add the stations to the line and save them
        ArrayList<Station> m7Stations = new ArrayList<>();
        m7Stations.add(gizaStation);
        m7Stations.add(cairoUniversityStation);
        m7Stations.add(nasrElDeenStation);
        m7Stations.add(talbiaStation);
        m7Stations.add(mariotiaStation);
        m7Stations.add(mashalStation);
        m7Stations.add(medanRyhmiaStation);
        m7Stations.forEach(station -> station.setTransportationType(TransportationType.BUS));
        stationRepo.saveAll(m7Stations);
        m7Stations.forEach(m7::addStation);
        lineRepo.save(m7);

        Graph graph = new Graph();
        graph.setOwner(mwasalatmisr);
        graph.setLine(m7);
        graphRepo.save(graph);
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        //
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(graph, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);


        HashMap<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m7_" + i, passwordEncoder.encode("1234"));
            bus.setLine(m7);
            movingTurnstiles.put(i, bus);
            userRepositry.save(bus);
        }

        Passenger omar = passengersRepo.findUserByUsername("omar");
        Passenger waled = passengersRepo.findUserByUsername("waled");
        Passenger aly = passengersRepo.findUserByUsername("aly");
        Passenger mona = passengersRepo.findUserByUsername("mona");

        ArrayList<Trip> trips = new ArrayList<>();
        trips.add(new Trip(
                omar,
                movingTurnstiles.get(0),
                null,
                simpleDateFormat.parse("2023-03-10 08:30:00"),
                null,
                TransportationType.BUS,
                10f,
                Status.Closed,
                gizaStation.getStationName(),
                medanRyhmiaStation.getStationName())
        );
        trips.add(new Trip(
                waled,
                movingTurnstiles.get(1),
                null,
                simpleDateFormat.parse("2023-03-10 08:45:00"),
                null,
                TransportationType.BUS,
                10f,
                Status.Closed,
                nasrElDeenStation.getStationName(),
                medanRyhmiaStation.getStationName())
        );
        trips.add(new Trip(
                aly,
                movingTurnstiles.get(2),
                null,
                simpleDateFormat.parse("2023-03-10 09:00:00"),
                null,
                TransportationType.BUS,
                10f,
                Status.Closed,
                cairoUniversityStation.getStationName(),
                medanRyhmiaStation.getStationName())
        );
        trips.add(new Trip(
                mona,
                movingTurnstiles.get(3),
                null,
                simpleDateFormat.parse("2023-03-10 09:15:00"),
                null,
                TransportationType.BUS,
                5f,
                Status.Closed,
                talbiaStation.getStationName(),
                medanRyhmiaStation.getStationName())
        );
        tripRepository.saveAll(trips);
    }

}
