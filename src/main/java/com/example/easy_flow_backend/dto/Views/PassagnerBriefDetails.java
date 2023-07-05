package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Gender;

import java.util.Date;

public interface PassagnerBriefDetails extends UserDetails {


    String  getUsername();

    Gender getGender();

    String getPhoneNumber();

    String getEmail();

    Date getBirthDay();


}
