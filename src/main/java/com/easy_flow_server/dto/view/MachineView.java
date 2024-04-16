package com.easy_flow_server.dto.view;

import org.springframework.beans.factory.annotation.Value;

public interface MachineView {

    //String getId();

    String getUsername();

    boolean getActive();
    @Value("#{target.owner.name}")
    String getOwnerName();

}
