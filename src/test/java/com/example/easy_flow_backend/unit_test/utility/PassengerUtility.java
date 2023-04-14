package com.example.easy_flow_backend.unit_test.utility;

import com.example.easy_flow_backend.entity.Gender;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.entity.Wallet;
import com.sun.xml.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
@SpringBootTest
public class PassengerUtility {

    @Autowired
    private PasswordEncoder passwordEncoder;
    Passenger makePassenger(String name){
        return new Passenger(new Wallet("CC"), name, "Fawzy", "01251253311", "Regular", "Cairo", Gender.M, new Date(System.currentTimeMillis()), "omar", passwordEncoder.encode("omar"), "omar@gmail.com");
    }
    Passenger makePassengerWithBalance(String name, double balance){
        Passenger passenger = makePassenger(name);
        passenger.getWallet().setBalance(balance);
        return passenger;
    }
}
