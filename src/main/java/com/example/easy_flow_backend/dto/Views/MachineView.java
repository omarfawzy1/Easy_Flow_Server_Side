package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.entity.Owner;
import com.example.easy_flow_backend.entity.Station;

import java.util.Optional;

public interface MachineView {

    String getId();

    String getUsername();

    boolean getActive();


}
