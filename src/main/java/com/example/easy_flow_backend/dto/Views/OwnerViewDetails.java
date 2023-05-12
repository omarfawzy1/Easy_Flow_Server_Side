package com.example.easy_flow_backend.dto.Views;

import java.util.List;

public interface OwnerViewDetails extends OwnerView {
    List<LineOwnerView> getLines();

    List<TicketView> getTickets();


}

interface LineOwnerView {
    String getName();
}