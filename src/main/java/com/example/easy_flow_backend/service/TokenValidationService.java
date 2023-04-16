package com.example.easy_flow_backend.service;

import com.auth0.jwt.JWT;
import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.dto.Models.Token;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.repos.AbstractRepo;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class TokenValidationService {
    @Autowired
    private PassengersRepo passengersRepo;

    public boolean validatePassengerToken(String tokenString, Date currGenerationTime){
        String[] chunks = tokenString.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        // TODO validate Token with Secret Key
        Token token = new Gson().fromJson(payload, Token.class);
        String username =  token.getSub();

        Passenger passenger = passengersRepo.findUserByUsername(username);
        Date lastPassengerGeneration = passenger.getLastQrTime();
        return currGenerationTime.compareTo(lastPassengerGeneration) > 0;
    }



}
