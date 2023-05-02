package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "owner_id")
    private String id;
    @Column(nullable = false, unique = true)
    private String name;

    private String mail;

    @Column(name = "bank_account")
    private String bankAccount;

    public Owner(String name, String mail, String bankAccount) {
        this.name = name;
        this.mail = mail;
        this.bankAccount = bankAccount;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return name.equals(owner.name) && mail.equals(owner.mail) && bankAccount.equals(owner.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mail, bankAccount);
    }
}