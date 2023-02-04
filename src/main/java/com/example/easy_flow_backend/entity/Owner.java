package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private static long counter = 0;
    @Id
//    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private String id;
    @Column(nullable = false)
    private String name;

    private String mail;

    @Column(name = "bank_account")
    private String bankAccount;

    public Owner(String name, String mail, String bankAccount) {
        this.id = "Owner-" + ++counter;
        this.name = name;
        this.mail = mail;
        this.bankAccount = bankAccount;

    }


}