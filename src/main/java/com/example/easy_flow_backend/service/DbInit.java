package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.easy_flow_backend.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Autowired
    private UserRepositry userRepositry;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private LineRepo lineRepo;
    @Autowired
    private StationRepo stationRepo;

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
        // save to database
        userRepositry.saveAll(users);
        // test database
        System.out.println(userRepositry.findUserByUsername("haridy").getUsername());
    }

    ArrayList<User> usersInit() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Administrator("haridy", "haridy", passwordEncoder.encode("haridy")));
        users.add(new Passenger(new Wallet("CC"), "Omar", "Fawzy", "01251253311", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "omar", passwordEncoder.encode("omar"), "omar@gmail.com"));
        users.add(new Passenger(new Wallet("CC", 9787654.68), "ALy", "Khaled", "01256156165", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "aLy", passwordEncoder.encode("omar"), "aly@gmail.com"));
        users.add(new Passenger(new Wallet("CC"), "Waled", "Yahia", "01254556464", "Regular", "Giza", Gender.M, new Date(System.currentTimeMillis()), "waled", passwordEncoder.encode("omar"), "waled@gmail.com"));
        users.add(new Passenger(new Wallet("CC"), "Mona", "Mahmoud", "12311561655", "Regular", "Cairo", Gender.F, new Date(System.currentTimeMillis()), "mona", passwordEncoder.encode("omar"), "mona@gmail.com"));
        //users.add(new StationaryTurnstile("giza_1_in",passwordEncoder.encode("1234")));
        return users;
    }

    void line2Init() {
        Owner cairoGovernment = new Owner("Cairo Government", "goverment@goverment.gov", "0000 0000 0000 0000");
        ownerRepo.save(cairoGovernment);
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

        ArrayList<Station> line2Stations = new ArrayList<>();
        line2Stations.add(elMonibStation);line2Stations.add(sakiatMekkiStation);line2Stations.add(ommElMisryeenStation);
        line2Stations.add(gizaStation);line2Stations.add(faysalStation);line2Stations.add(cairoUniversityStation);
        line2Stations.add(elBohoosStation);line2Stations.add(dokkiStation);line2Stations.add(operaStation);

        lineRepo.save(line2);
        stationRepo.saveAll(line2Stations);
        line2Stations.forEach(line2::addStation);
        lineRepo.save(line2);

        line2Stations.forEach((station -> {
            StationaryTurnstile stationaryTurnstile = new StationaryTurnstile(
                    String.format("%s_%d_in",station.getStationName().toLowerCase().replaceAll(" ",""),userRepositry.count())
                    , passwordEncoder.encode("1234"));
                stationaryTurnstile.setStation(station);
                userRepositry.save(stationaryTurnstile);
        }));

    }


}
