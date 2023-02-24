package com.example.easy_flow_backend.Dto.Views;

import com.example.easy_flow_backend.entity.Owner;

import java.util.Set;

public interface LineView {
    String getId();

    float getPrice();

    Owner getOwner();

    Set<String> getStationsId();
}
