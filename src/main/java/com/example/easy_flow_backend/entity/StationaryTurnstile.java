package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stationary_turnstile")
public class StationaryTurnstile extends User {
    static long counter=0;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;
    public StationaryTurnstile(String username, String password) {
        super(username, password);
        id="StationaryTurnstile-"+ ++counter;
    }


}
