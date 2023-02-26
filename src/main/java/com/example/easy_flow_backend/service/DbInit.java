package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.repos.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DbInit implements CommandLineRunner {
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
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
    private TicketRepo ticketRepository;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


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

        // StationaryTurnstile
        StationaryTurnstile stationaryTurnstile = new StationaryTurnstile("sTurnstile1", passwordEncoder.encode("sTurnstile1"));
        stationaryTurnstile.setStation(station1);
        users.add(stationaryTurnstile);
        line2Init();
        MovingTurnstile movingTurnstile = new MovingTurnstile("mTurnstile1", passwordEncoder.encode("mTurnstile1"));
        movingTurnstile.setLine(line1);
        users.add(movingTurnstile);
        // save to database
        userRepositry.saveAll(users);
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

        // Create some tickets
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(
                omar,
                stationaryTurnstiles.get(gizaStation.getStationName()),
                stationaryTurnstiles.get(elMonibStation.getStationName()),
                simpleDateFormat.parse("2023-11-01 09:54:31"),
                simpleDateFormat.parse("2023-11-01 11:40:22"),
                40f,
                Status.Closed)
        );

        // Ticket for Aly
        tickets.add(new Ticket(
                aly,
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                stationaryTurnstiles.get(dokkiStation.getStationName()),
                simpleDateFormat.parse("2023-11-02 08:30:00"),
                simpleDateFormat.parse("2023-11-02 09:15:00"),
                20f,
                Status.Closed)
        );

        // Ticket for Waled
        tickets.add(new Ticket(
                waled,
                stationaryTurnstiles.get(elBohoosStation.getStationName()),
                stationaryTurnstiles.get(faysalStation.getStationName()),
                simpleDateFormat.parse("2023-11-03 12:45:00"),
                simpleDateFormat.parse("2023-11-03 13:20:00"),
                10f,
                Status.Closed)
        );

        // Ticket for Mona
        tickets.add(new Ticket(
                mona,
                stationaryTurnstiles.get(gizaStation.getStationName()),
                stationaryTurnstiles.get(ommElMisryeenStation.getStationName()),
                simpleDateFormat.parse("2023-11-04 17:30:00"),
                simpleDateFormat.parse("2023-11-04 18:10:00"),
                15f,
                Status.Closed)
        );

        // Ticket for Omar
        tickets.add(new Ticket(
                omar,
                stationaryTurnstiles.get(operaStation.getStationName()),
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                simpleDateFormat.parse("2023-11-05 10:00:00"),
                simpleDateFormat.parse("2023-11-05 10:45:00"),
                20f,
                Status.Closed)
        );
        ticketRepository.saveAll(tickets);

        tickets.add(new Ticket(
                waled,
                stationaryTurnstiles.get(cairoUniversityStation.getStationName()),
                stationaryTurnstiles.get(elBohoosStation.getStationName()),
                simpleDateFormat.parse("2023-03-10 08:30:00"),
                simpleDateFormat.parse("2023-03-10 09:00:00"),
                10f,
                Status.Closed)
        );

        tickets.add(new Ticket(
                aly,
                stationaryTurnstiles.get(sakiatMekkiStation.getStationName()),
                stationaryTurnstiles.get(operaStation.getStationName()),
                simpleDateFormat.parse("2023-04-02 17:15:00"),
                simpleDateFormat.parse("2023-04-02 18:05:00"),
                7f,
                Status.Closed)
        );

        tickets.add(new Ticket(
                mona,
                stationaryTurnstiles.get(ommElMisryeenStation.getStationName()),
                stationaryTurnstiles.get(dokkiStation.getStationName()),
                simpleDateFormat.parse("2023-05-21 13:40:00"),
                simpleDateFormat.parse("2023-05-21 14:15:00"),
                5f,
                Status.Closed)
        );

    }
}
