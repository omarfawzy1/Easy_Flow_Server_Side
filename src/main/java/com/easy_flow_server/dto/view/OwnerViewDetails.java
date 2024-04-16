package com.easy_flow_server.dto.view;

import java.util.List;

public interface OwnerViewDetails extends OwnerView {
    List<LineOwnerView> getLines();

    List<TicketView> getTickets();


}

interface LineOwnerView {
    String getName();
}