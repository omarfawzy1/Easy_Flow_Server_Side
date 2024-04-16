package com.easy_flow_server.dto.view;

import com.easy_flow_server.entity.Gender;

import java.util.Date;

public interface PassagnerBriefDetails extends UserDetails {


    String  getUsername();

    Gender getGender();

    String getPhoneNumber();

    String getEmail();

    Date getBirthDay();


}
