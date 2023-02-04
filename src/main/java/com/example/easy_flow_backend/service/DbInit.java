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
    public void run(String... args)  {
        //Delete all
        //this.userRepositry.deleteAll();
        //creat users and save in database
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Line> lines=new ArrayList<>();

        //Passenger herher =new Passenger("2","harhor");

        Administrator haridy = new Administrator("haridy","haridy",passwordEncoder.encode("haridy"));

        // Create a new Passenger object using the constructor
        Wallet wallet = new Wallet("CC");
        Date birthDate = new Date(System.currentTimeMillis());
        walletRepo.save(wallet);
        Passenger omar = new Passenger(wallet, "Omar", "Fawzy", "01251253311", "Regular", "Cairo", Gender.M,birthDate ,"omar",passwordEncoder.encode("omar"));

        users.add(haridy);
        users.add(omar);


        //owner
        Owner owner1 =new Owner("ehab","ehab@mail.com","1148 1124 2247 2247");
        this.ownerRepo.save(owner1);
        //line and station
        Line line1=new Line(10.0F,owner1);
        Line line2=new Line(31.0F,owner1);
        Station station1=new Station("first station");

        lines.add(line1);
        lines.add(line2);
        this.lineRepo.saveAll(lines);

        this.stationRepo.save(station1);
        line1.addStation(station1);
        line2.addStation(station1);
        this.lineRepo.saveAll(lines);

        //StationaryTurnstile
        StationaryTurnstile stationaryTurnstile=
                new StationaryTurnstile("sTurnstile1",passwordEncoder.encode("sTurnstile1"));
        stationaryTurnstile.setStation(station1);
        users.add(stationaryTurnstile);

        //save to database
        this.userRepositry.saveAll(users);
        User user = userRepositry.findUserByUsername("haridy");
        System.out.println(user.getUsername());
        List<User> arr = userRepositry.findAll();
        user.getUsername();
    }
}
