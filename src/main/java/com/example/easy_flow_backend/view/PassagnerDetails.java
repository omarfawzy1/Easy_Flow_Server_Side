package com.example.easy_flow_backend.view;

import com.example.easy_flow_backend.entity.Wallet;
import lombok.NoArgsConstructor;

import java.util.Date;

public interface PassagnerDetails extends UserDetails {

    String getFirstName();
    String getLastName();
    String getCity();
     String getType();
    String getGender();
     String getPhoneNumber();
     Date getBirthDay();
     boolean getActive();
     Wallet getWallet();
}
