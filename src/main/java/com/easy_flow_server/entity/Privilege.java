package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "privilege_id")
    private String id;
    @Column(nullable = false, unique = true)
    String name;
    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    Set<Passenger> passengers = new HashSet<>();
    public Privilege(String name){
        this.name=name;
    }
}
