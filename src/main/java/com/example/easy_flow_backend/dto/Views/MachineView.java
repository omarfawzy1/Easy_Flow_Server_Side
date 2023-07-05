package com.example.easy_flow_backend.dto.Views;

import org.springframework.beans.factory.annotation.Value;

public interface MachineView {

    //String getId();

    String getUsername();

    boolean getActive();
    @Value("#{target.owner.name}")
    String getOwnerName();

}
