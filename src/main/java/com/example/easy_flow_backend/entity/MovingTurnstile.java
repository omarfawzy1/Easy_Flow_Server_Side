package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Moving_turnstile")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovingTurnstile extends Turnstile{

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name= "line_id", nullable = true)
    private Line line;
    public MovingTurnstile(String username, String password, Owner owner) {
        super(username, password, owner);
    }
}
