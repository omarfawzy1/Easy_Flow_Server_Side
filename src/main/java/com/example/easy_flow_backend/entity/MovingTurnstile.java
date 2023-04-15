package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Moving_turnstile")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovingTurnstile extends Turnstile{

    @ManyToOne
    @JoinColumn(name= "line_id")
    private Line line;
    public MovingTurnstile(String username, String password, Owner owner) {
        super(username, password, owner);
    }
}
