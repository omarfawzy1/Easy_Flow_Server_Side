package com.example.easy_flow_backend.view;

import java.util.Date;

public interface PassagnerDetails extends UserDetails {
     String getType();
    String getGender();
     String getPhoneNumber();
     Date getBirthDay();
     boolean getActive();
}
