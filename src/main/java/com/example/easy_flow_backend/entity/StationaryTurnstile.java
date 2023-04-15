package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stationary_turnstile")
public class StationaryTurnstile extends Turnstile {
    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;
    public StationaryTurnstile(String username, String password) {
        super(username, password);
    }


}
