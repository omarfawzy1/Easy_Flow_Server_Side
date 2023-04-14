package com.example.easy_flow_backend.unit_test.utility;

import com.example.easy_flow_backend.entity.Owner;

public class OwnerUtility {
    public static Owner makeOwner(){
        return new Owner("Omar", "omar@gmail.com", "0000 0000 0000 0000");
    }
}
