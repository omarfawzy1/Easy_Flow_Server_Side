package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Owner {



    @Id
    @Column(name = "owner_id")
    private String id;
    @Column(nullable = false)
    private String name;

    private String mail;

    @Column(name = "bank_account")
    private String bankAccount;
    public Owner(){};

    public Owner(String id,String name, String mail, String bankAccount) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.bankAccount = bankAccount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return mail;
    }


    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", e-mail='" + mail + '\'' +
                ", bank_account='" + bankAccount + '\'' + "} \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner that = (Owner) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(mail, that.mail) && Objects.equals(bankAccount, that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mail, bankAccount);
    }
}