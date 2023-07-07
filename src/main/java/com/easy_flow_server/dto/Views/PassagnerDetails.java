package com.easy_flow_server.dto.Views;

import com.easy_flow_server.entity.Gender;
import com.easy_flow_server.entity.Wallet;

import java.util.Date;

public interface PassagnerDetails extends UserDetails {

    String getFirstName();

    String getLastName();

    String getCity();

    Gender getGender();

    String getPhoneNumber();

    String getEmail();

    Date getBirthDay();

    boolean getActive();

    Wallet getWallet();

}
