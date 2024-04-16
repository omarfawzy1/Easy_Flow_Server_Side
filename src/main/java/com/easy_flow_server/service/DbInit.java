package com.easy_flow_server.service;

import com.easy_flow_server.entity.*;
import com.easy_flow_server.repository.*;
import com.easy_flow_server.service.station_line.LineService;
import com.easy_flow_server.service.utils.Utility;
import lombok.SneakyThrows;
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

    private final UserRepo userRepo;
    private final PassengerRepo passengerRepo;

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepo ownerRepo;
    private final LineRepo lineRepo;
    private final StationRepo stationRepo;
    private final TripRepo tripRepository;
    private final GraphEdgeRepo graphEdgeRepo;
    private final TicketRepo ticketRepo;
    private final LineService lineService;
    private final PlanRepo planRepo;
    private final PrivilegeRepo privilegeRepo;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    ArrayList<Privilege> privileges = new ArrayList<>();

    public DbInit(PassengerRepo passengerRepo, UserRepo userRepo, PasswordEncoder passwordEncoder, OwnerRepo ownerRepo, LineRepo lineRepo, LineService lineService, PrivilegeRepo privilegeRepo, StationRepo stationRepo, TripRepo tripRepository, PlanRepo planRepo, GraphEdgeRepo graphEdgeRepo, TicketRepo ticketRepo) {
        this.passengerRepo = passengerRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.ownerRepo = ownerRepo;
        this.lineRepo = lineRepo;
        this.lineService = lineService;
        this.privilegeRepo = privilegeRepo;
        this.stationRepo = stationRepo;
        this.tripRepository = tripRepository;
        this.planRepo = planRepo;
        this.graphEdgeRepo = graphEdgeRepo;
        this.ticketRepo = ticketRepo;
    }


    @Override
    public void run(String... args) throws ParseException {

        privileges.add(new Privilege("Regular"));
        privileges.add(new Privilege("Army"));
        privileges.add(new Privilege("Elder"));
        privileges.add(new Privilege("journalist"));
        privilegeRepo.saveAll(privileges);


        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<User> users = usersInit();
        userRepo.saveAll(users);
        //add passengers privlages
        ((Passenger) users.get(1)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        ((Passenger) users.get(1)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Army"));
        ((Passenger) users.get(2)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        ((Passenger) users.get(3)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        ((Passenger) users.get(3)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Elder"));
        ((Passenger) users.get(4)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Regular"));
        ((Passenger) users.get(4)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("Army"));
        ((Passenger) users.get(4)).addPrivilege(privilegeRepo.findPrivilegeByNameIgnoreCase("journalist"));
        userRepo.saveAll(users);
        //owner
        Owner owner1 = new Owner("ehab", "ehab@mail.com", "1148 1124 2247 2247");
        ownerRepo.save(owner1);

        //line and station
        Line line1 = new Line("line1", TransportationType.BUS, owner1);
        Line line2 = new Line("line2", TransportationType.BUS, owner1);
        Station station1 = new Station("first station");
        Station station2 = new Station("second station");
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
        addplans();
        // test database
        System.out.println(userRepo.findUserByUsername("haridy").getUsername());
    }

    ArrayList<User> usersInit() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Administrator("haridy", "haridy", passwordEncoder.encode("haridy")));
        users.add(new Passenger(new Wallet("CC"), "Omar", "Fawzy", "01090717384", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "omar", passwordEncoder.encode("omar"), "omar552001@hotmail.com"));
        users.add(new Passenger(new Wallet("CC", 9787654.68), "ALy", "Khaled", "01032816048", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "aly", passwordEncoder.encode("omar"), "mohamed.haridi.14@gmail.com"));
        users.add(new Passenger(new Wallet("CC"), "Waled", "Yahia Mohsen", "01254556464", "Giza", Gender.M, new Date(System.currentTimeMillis()), "waled", passwordEncoder.encode("omar"), "waled@gmail.com"));
        users.add(new Passenger(new Wallet("CC", 1000.0), "Mona", "Mahmoud", "12311561655", "Cairo", Gender.F, new Date(System.currentTimeMillis()), "mona", passwordEncoder.encode("omar"), "mona@gmail.com"));
        return users;
    }

    @SneakyThrows
    void line2Init() {
        // Create and save the owner of the line
        Owner cairoGovernment = new Owner("Cairo Government", "government@government.gov", "0000 0000 0000 0000");
        ownerRepo.save(cairoGovernment);
        Line line1 = new Line("Line 1", TransportationType.METRO, cairoGovernment);
        Station helwan = new Station("Helwan");
        Station ainHelwan = new Station("Ain Helwan");
        Station helwanUniversity = new Station("Helwan University");
        Station wadi_hof = new Station("Wadi Hof");
        Station hadayek_helwan = new Station("Hadayek Helwan");
        Station maasara = new Station("El-Maasara");
        Station toraasment = new Station("Tora El-Asmant");
        Station kozzika = new Station("Kozzika");
        Station toraElBalad = new Station("Tora El-Balad");
        Station sakanatElMaadi = new Station("Sakanat El-Maadi");
        Station maadi = new Station("Maadi");
        Station hadyekElmaadi = new Station("Hadayek El-Maadi");
        Station darElSalam = new Station("Dar El-Salam");
        Station ElZagraa = new Station("El-Zahraa'");
        Station MarGitgis = new Station("Mar Girgis");
        Station ElmalekElsaleh = new Station("El-Malek El-Saleh");
        Station AlSayedaZeinab = new Station("Al-Sayeda Zeinab");
        Station saadZaghloul = new Station("Saad Zaghloul");
        Station sadat = new Station("Sadat");
        Station nasser = new Station("Nasser");
        Station orabi = new Station("Orabi");
        Station alShohadaa = new Station("Al-Shohadaa");
        Station ghamra = new Station("Ghamra");
        Station elDemerdash = new Station("El-Demerdash");
        Station manshya = new Station("Manshiet El-Sadr");
        Station kobriqobba = new Station("Kobri El-Qobba");
        Station hammamat = new Station("Hammamat El-Qobba");
        Station saray = new Station("Saray El-Qobba");
        Station zaitoun = new Station("Hadayeq El-Zaitoun");
        Station helmeyet = new Station("Helmeyet El-Zaitoun");
        Station matareyya = new Station("El-Matareyya");
        Station ainShams = new Station("Ain Shams");
        Station ezbetElNakhl = new Station("Ezbet El-Nakhl");
        Station elmarg = new Station("El-Marg");
        Station elmargelgedid = new Station("New El-Marg");
        List<Station> line1Stations = new ArrayList<>();
        line1Stations.add(helwan);
        line1Stations.add(ainHelwan);
        line1Stations.add(helwanUniversity);
        line1Stations.add(wadi_hof);
        line1Stations.add(hadayek_helwan);
        line1Stations.add(maasara);
        line1Stations.add(toraasment);
        line1Stations.add(kozzika);
        line1Stations.add(toraElBalad);
        line1Stations.add(sakanatElMaadi);
        line1Stations.add(maadi);
        line1Stations.add(hadyekElmaadi);
        line1Stations.add(darElSalam);
        line1Stations.add(ElZagraa);
        line1Stations.add(MarGitgis);
        line1Stations.add(ElmalekElsaleh);
        line1Stations.add(AlSayedaZeinab);
        line1Stations.add(saadZaghloul);
        line1Stations.add(sadat);
        line1Stations.add(nasser);
        line1Stations.add(orabi);
        line1Stations.add(alShohadaa);
        line1Stations.add(ghamra);
        line1Stations.add(elDemerdash);
        line1Stations.add(manshya);
        line1Stations.add(kobriqobba);
        line1Stations.add(hammamat);
        line1Stations.add(saray);
        line1Stations.add(zaitoun);
        line1Stations.add(helmeyet);
        line1Stations.add(matareyya);
        line1Stations.add(ainShams);
        line1Stations.add(ezbetElNakhl);
        line1Stations.add(elmarg);
        line1Stations.add(elmargelgedid);

        // Create the line and its stations
        Line line2 = new Line("Line 2", TransportationType.METRO, cairoGovernment);
        Station elMonibStation = new Station("El-Mounib");
        Station sakiatMekkiStation = new Station("Sakiat Mekki");
        Station ommElMisryeenStation = new Station("Omm el Misryeen");
        Station gizaStation = new Station("Giza");
        Station faysalStation = new Station("Faisal");
        Station cairoUniversityStation = new Station("Cairo University");
        Station elBohoosStation = new Station("El Bohoos");
        Station dokkiStation = new Station("Dokki");
        Station operaStation = new Station("Opera");
        Station mohamedNaguib = new Station("Mohamed Naguib");
        Station masarra = new Station("Masarra");
        Station roadfarag = new Station("Road El-Farag");
        Station teresa = new Station("St. Teresa");
        Station khalafawy = new Station("Khalafawy");
        Station mezallat = new Station("Mezallat");
        Station kolleyazeraa = new Station("Kolleyyet El-Zeraa");
        Station shubra = new Station("Shubra El-Kheima");
        Station attaba = new Station("Attaba");

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
        line2Stations.add(sadat);
        line2Stations.add(mohamedNaguib);
        line2Stations.add(attaba);
        line2Stations.add(alShohadaa);
        line2Stations.add(masarra);
        line2Stations.add(roadfarag);
        line2Stations.add(teresa);
        line2Stations.add(khalafawy);
        line2Stations.add(mezallat);
        line2Stations.add(kolleyazeraa);
        line2Stations.add(shubra);

        //        line2Stations.forEach(station -> station.setTransportationType(TransportationType.METRO));
        line1 = lineRepo.save(line1);
        line2 = lineRepo.save(line2);
        for (Station station : line1Stations) {
            line1 = lineService.addStationToLine(station, line1);
        }
//        line1Stations.forEach(station -> stationRepo.findByStationName(station.getStationName()));

        for (Station station : line2Stations) {
            line2 = lineService.addStationToLine(station, line2);
        }
        line2Stations.forEach(station -> stationRepo.findByStationName(station.getStationName()));

//        line2Stations = line2.getStations().stream().toList();
        Ticket line2Ticket = new Ticket(
                cairoGovernment, line2, 20, 9, Utility.stringToMilleSecond("0000-00-00/02-00-00"));
        ticketRepo.save(line2Ticket);
        Ticket line4Ticket = new Ticket(
                cairoGovernment, line2, 9, 4, Utility.stringToMilleSecond("0000-00-00/00-30-00"));
        Ticket line3Ticket = new Ticket(
                cairoGovernment, line2, 12, 7, Utility.stringToMilleSecond("0000-00-00/03-00-00"));
        ticketRepo.save(line4Ticket);
        ticketRepo.save(line3Ticket);

        ArrayList<GraphEdge> graphEdges1 = new ArrayList<>();
        for (int i = 0; i < line1Stations.size() - 1; i++) {
            graphEdges1.add(new GraphEdge(line1, line1Stations.get(i), line1Stations.get(i + 1), 1D));
        }

        graphEdgeRepo.saveAll(graphEdges1);

        ArrayList<GraphEdge> graphEdges2 = new ArrayList<>();
        for (int i = 0; i < line2Stations.size() - 1; i++) {
            graphEdges2.add(new GraphEdge(line2, line2Stations.get(i), line2Stations.get(i + 1), 1D));
        }

        graphEdgeRepo.saveAll(graphEdges2);

        // Create and save stationary turnstiles for each station
        HashMap<String, StationaryTurnstile> stationaryTurnstiles = new HashMap<>();
        for (Station station : line2Stations) {
            String turnstileName = String.format("%s_%d_in", station.getStationName().toLowerCase().replaceAll(" ", ""), userRepo.count());
            String password = passwordEncoder.encode("1234");
            StationaryTurnstile stationaryTurnstile = new StationaryTurnstile(turnstileName, password, cairoGovernment);
            stationaryTurnstile.setStation(station);
            stationaryTurnstiles.put(station.getStationName(), stationaryTurnstile);
            userRepo.save(stationaryTurnstile);
        }
        for (Station station : line1Stations) {
            String turnstileName = String.format("%s_%d_in", station.getStationName().toLowerCase().replaceAll(" ", ""), userRepo.count());
            String password = passwordEncoder.encode("1234");
            StationaryTurnstile stationaryTurnstile = new StationaryTurnstile(turnstileName, password, cairoGovernment);
            stationaryTurnstile.setStation(station);
            stationaryTurnstiles.put(station.getStationName(), stationaryTurnstile);
            userRepo.save(stationaryTurnstile);
        }

        // s1=elmonib_5_in    s2= giza_7_in
        // Create some passengers
        Passenger omar = passengerRepo.findUserByUsername("omar");
        Passenger waled = passengerRepo.findUserByUsername("waled");
        Passenger aly = passengerRepo.findUserByUsername("aly");
        Passenger mona = passengerRepo.findUserByUsername("mona");

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
                elMonibStation.getStationName(), 1)
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
                dokkiStation.getStationName(), 1)
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
                faysalStation.getStationName(), 1)
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
                ommElMisryeenStation.getStationName(), 1)
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
                sakiatMekkiStation.getStationName(), 1)
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
                elBohoosStation.getStationName(), 1)
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
                operaStation.getStationName(), 1)
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
                dokkiStation.getStationName(), 1)
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

        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        // TODO Fix the set order
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(m7, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);


        HashMap<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m7_" + i, passwordEncoder.encode("1234"), mwasalatmisr);
            bus.setLine(m7);
            movingTurnstiles.put(i, bus);
            userRepo.save(bus);
        }

        Passenger omar = passengerRepo.findUserByUsername("omar");
        Passenger waled = passengerRepo.findUserByUsername("waled");
        Passenger aly = passengerRepo.findUserByUsername("aly");
        Passenger mona = passengerRepo.findUserByUsername("mona");

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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
        );
        for (Trip t : trips) {
            t.setEndTime(t.getStartTime());
        }
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
        //stationRepo.saveAll(m7Stations);

        m8 = lineRepo.save(m8);
        for (Station station : m7Stations) {
            m8 = lineService.addStationToLine(station, m8);
        }
        m7Stations = m8.getStations().stream().toList();

        Ticket m8Ticket = new Ticket(
                mwasalatmisr, m8, 7.5, 7, Utility.stringToMilleSecond("0000-00-00/01-00-00"));
        ticketRepo.save(m8Ticket);


        ArrayList<GraphEdge> graphEdges = new ArrayList<>();
        //
        for (int i = 0; i < m7Stations.size() - 1; i++) {
            graphEdges.add(new GraphEdge(m8, m7Stations.get(i), m7Stations.get(i + 1), 1D));
        }
        graphEdgeRepo.saveAll(graphEdges);


        HashMap<Integer, MovingTurnstile> movingTurnstiles = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            MovingTurnstile bus = new MovingTurnstile("m8_" + i, passwordEncoder.encode("1234"), mwasalatmisr);
            bus.setLine(m8);
            movingTurnstiles.put(i, bus);
            userRepo.save(bus);
        }

        Passenger omar = passengerRepo.findUserByUsername("omar");
        Passenger waled = passengerRepo.findUserByUsername("waled");
        Passenger aly = passengerRepo.findUserByUsername("aly");
        Passenger mona = passengerRepo.findUserByUsername("mona");

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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
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
                medanRyhmiaStation.getStationName(), 1)
        );
        for (Trip t : trips) {
            t.setEndTime(t.getStartTime());
        }
        tripRepository.saveAll(trips);

    }

    @SneakyThrows
    void addplans() {
        Owner owner1 = ownerRepo.findByName("Cairo government");
        Owner owner2 = ownerRepo.findByName("mwasalatmisr");
        Plan plan1 = new Plan(privileges.get(0), 20, 30, 60, "Gold", 3, owner1, 0.5F);
        Plan plan2 = new Plan(privileges.get(1), 5, 90, 120, "Silver", 1, owner1, 0.8F);
        Plan plan3 = new Plan(privileges.get(2), 10, 20, 40, "Silver", 1, owner2, 0.3F);
        Plan plan4 = new Plan(privileges.get(3), 3, 30, 10, "Army Silver", 1, owner2, 0.5F);
        List<Plan> plans = new ArrayList<>();
        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        plans.add(plan4);
        planRepo.saveAll(plans);
    }
}
