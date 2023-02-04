package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {


    @Id
    @Column(name = "owner_id")
    private String id;
    @Column(nullable = false)
    private String name;

    private String mail;

    @Column(name = "bank_account")
    private String bankAccount;


}