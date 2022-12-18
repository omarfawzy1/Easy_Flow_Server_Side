package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.User;
import com.example.easy_flow_backend.entity.Wallet;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Service;
import com.example.easy_flow_backend.entity.*;

import java.util.ArrayList;

@Service
public class DbInit implements CommandLineRunner {
    @Autowired
    private UserRepositry userRepositry;

    @Override
    public void run(String... args)  {
        //Delete all
        this.userRepositry.deleteAll();
        //creat users and save in database
        ArrayList<User> users = new ArrayList<>();
        //Wallet wallet = new Wallet();
//        Passenger omar = new Passenger();
//        omar.setId(10);
//        omar.setUserDetails("omar",passwordEncoder.encode("1234"));
//        omar.setWallet_fk(wallet);
//        omar.setFirst_name("omar");
//        omar.setLast_name("mohamed");

        Passenger herher =new Passenger("2","harhor");
        Administrator haridy = new Administrator("1","haridy","haridy","haridy");
        //haridy.setUserDetails("haridy","haridy123");
        //users.add(new User("haridy", passwordEncoder.encode("haridy123"), "USER", ""));
        //users.add(new User("admin", passwordEncoder.encode("admin123"), "ADMIN", "ACCESS_TEST1,ACCESS_TEST2"));
        //users.add(new User("manager", passwordEncoder.encode("manager123"), "MANAGER", "ACCESS_TEST1"));
        users.add(haridy);
        users.add(herher);
        //users.add(omar);

        //save to database
        this.userRepositry.saveAll(users);
        User user = userRepositry.findUserByUsername("haridy");
        System.out.println(user.getUsername());

    }
}
