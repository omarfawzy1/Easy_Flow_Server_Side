package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.service.station_line_services.LineService;
import com.example.easy_flow_backend.service.utils.Utility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private LineService lineService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Override
    public void run(String... args) throws ParseException {
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<User> users = usersInit();

        //owner
        Owner owner1 = new Owner("ehab", "ehab@mail.com", "1148 1124 2247 2247");
        ownerRepo.save(owner1);

        //line and station
        Line line1 = new Line("line1", TransportationType.BUS, owner1);
        Line line2 = new Line("line2", TransportationType.BUS, owner1);
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
        Ticket line1Ticket = new Ticket(
                owner1, line1, 3, 5, Utility.stringToMilleSecond("0000-00-00/00-30-00"));
        Ticket line2Ticket = new Ticket(
                owner1, line1, 1, 16, Utility.stringToMilleSecond("0000-00-00/03-00-00"));
        ticketRepo.save(line1Ticket);
        ticketRepo.save(line2Ticket);

        line2Init();
        m7Init();
        m8Init();
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
        Line line2 = new Line("Line 2", TransportationType.METRO, cairoGovernment);
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
        List<Station> line2Stations = new ArrayList<>();
        line2Stations.add(elMonibStation);
        line2Stations.add(sakiatMekkiStation);
        line2Stations.add(ommElMisryeenStation);
        line2Stations.add(gizaStation);
        line2Stations.add(faysalStation);
        line2Stations.add(cairoUniversityStation);
        line2Stations.add(elBohoosStation);
        line2Stations.add(dokkiStation);
        line2Stations.add(operaStation);
//        line2Stations.forEach(station -> station.setTransportationType(TransportationType.METRO));

        line2 = lineRepo.save(line2);

        for (Station station : line2Stations) {
            line2 = lineService.addStationToLine(station, line2);
        }
        line2Stations = line2.getStations().stream().toList();
        Ticket line2Ticket = new Ticket(
                cairoGovernment, line2, 20, 9, Utility.stringToMilleSecond("0000-00-00/02-00-00"));
        ticketRepo.save(line2Ticket);

        // Create and save stationary turnstiles for each station
        HashMap<String, StationaryTurnstile> stationaryTurnstiles = new HashMap<>();
        for (Station station : line2Stations) {
            String turnstileName = String.format("%s_%d_in", station.getStationName().toLowerCase().replaceAll(" ", ""), userRepositry.count());
            String password = passwordEncoder.encode("1234");
            StationaryTurnstile stationaryTurnstile = new StationaryTurnstile(turnstileName, password, cairoGovernment);
            stationaryTurnstile.setStation(station);
            stationaryTurnstiles.put(station.getStationName(), stationaryTurnstile);
            userRepositry.save(stationaryTurnstile);
        }

        // s1=elmonib_5_in    s2= giza_7_in
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

        Line m7 = new Line("m7", TransportationType.BUS, mwasalatmisr);

        Station gizaStation = new Station("Giza");
        Station cairoUniversityStation = new Station("Cairo University");
        Station nasrElDeenStation = new Station("Nasr el Deen");
        Station talbiaStation = new Station("Talbia");
        Station mariotiaStation = new Station("Mariotia");
        Station mashalStation = new Station("Mashal");
        Station medanRyhmiaStation = new Station("Medan Ryhmia");


        // Add the stations to the line and save them
        List<Station> m7Stations = new ArrayList<>();
        m7Stations.add(gizaStation);
        m7Stations.add(cairoUniversityStation);
        m7Stations.add(nasrElDeenStation);
        m7Stations.add(talbiaStation);
        m7Stations.add(mariotiaStation);
        m7Stations.add(mashalStation);
        m7Stations.add(medanRyhmiaStation);

        m7 = lineRepo.save(m7);
        for (Station station : m7Stations) {
            m7 = lineService.addStationToLine(station, m7);
        }
        m7Stations = m7.getStations().stream().toList();
        Ticket m7Ticket = new Ticket(
                mwasalatmisr, m7, 7.5, 7, Utility.stringToMilleSecond("0000-00-00/01-30-00"));
        ticketRepo.save(m7Ticket);

        Graph graph = new Graph();
        graph.setOwner(mwasalatmisr);
        graph.setLine(m7);
        graphRepo.save(graph);
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        // TODO Fix the set order
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(graph, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);


        HashMap<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m7_" + i, passwordEncoder.encode("1234"), mwasalatmisr);
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

    @SneakyThrows
    void m8Init() {
        Owner mwasalatmisr = ownerRepo.findByName("mwasalatmisr");
        Line m8 = new Line("m8", TransportationType.BUS, mwasalatmisr);

        Station gizaStation = new Station("Mo5abrat");
        Station cairoUniversityStation = new Station("Mall Of egypt");
        Station nasrElDeenStation = new Station("SouqElgomla");
        Station talbiaStation = new Station("Frdous");
        Station mariotiaStation = new Station("Boabat");
        Station mashalStation = stationRepo.findByStationName("Mashal");
        Station medanRyhmiaStation = stationRepo.findByStationName("Medan Ryhmia");


        // Add the stations to the line and save them
        List<Station> m7Stations = new ArrayList<>();
        m7Stations.add(gizaStation);
        m7Stations.add(cairoUniversityStation);
        m7Stations.add(nasrElDeenStation);
        m7Stations.add(talbiaStation);
        m7Stations.add(mariotiaStation);
        m7Stations.add(mashalStation);
        m7Stations.add(medanRyhmiaStation);
//        m7Stations.forEach(station -> station.setTransportationType(TransportationType.BUS));
        stationRepo.saveAll(m7Stations);

        m8 = lineRepo.save(m8);
        for (Station station : m7Stations) {
            m8 = lineService.addStationToLine(station, m8);
        }
        m7Stations = m8.getStations().stream().toList();

        Ticket m8Ticket = new Ticket(
                mwasalatmisr, m8, 7.5, 7, Utility.stringToMilleSecond("0000-00-00/01-00-00"));
        ticketRepo.save(m8Ticket);

        Graph graph = new Graph();
        graph.setOwner(mwasalatmisr);
        graph.setLine(m8);
        graphRepo.save(graph);
        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        //
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(graph, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);


        HashMap<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m8_" + i, passwordEncoder.encode("1234"), mwasalatmisr);
            bus.setLine(m8);
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
