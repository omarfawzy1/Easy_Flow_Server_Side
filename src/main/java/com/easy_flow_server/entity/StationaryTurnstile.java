package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stationary_turnstile")
public class StationaryTurnstile extends Turnstile {
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id", nullable = true)
    private Station station;

    public StationaryTurnstile(String username, String password, Owner owner) {
        super(username, password, owner);
    }


}
