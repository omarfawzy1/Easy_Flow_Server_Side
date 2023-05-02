package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.Token;
import com.example.easy_flow_backend.entity.Passenger;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

import static com.example.easy_flow_backend.security.JwtProperties.SECRET;

@Service
public class TokenValidationService {
    @Autowired
    private PassengersRepo passengersRepo;


    public boolean validateGenerationTime(Date currGenerationTime, String username) {
        Passenger passenger = passengersRepo.findUserByUsername(username);
        Date lastPassengerGeneration = passenger.getLastQrTime();

        return currGenerationTime.compareTo(lastPassengerGeneration) > 0;
    }

    public boolean validatePassengerToken(String tokenString, String username) {
        String[] chunks = tokenString.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        //validate
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), sa.getJcaName());

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

        //if can not validate token signature
        if (!validator.isValid(tokenWithoutSignature, signature)) return false;

        Token token = new Gson().fromJson(payload, Token.class);
        String tokenUsername = token.getSub();

        return username.equals(tokenUsername);
    }


}
