package com.example.easy_flow_backend.entity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Owner {
    public Owner(){};

    public Owner(String owner_id,String name, String mail, String password, String bank_account) {
        this.owner_id = owner_id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.bank_account = bank_account;
    }

    @Id
    private String owner_id;
    private String name;
    private String mail;
    private String password;
    private String bank_account;

    public String getId() {
        return owner_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getBankAccount() {
        return bank_account;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + owner_id + '\'' +
                ", name='" + name + '\'' +
                ", e-mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", bank_account='" + bank_account + '\'' + "} \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        Owner that = (Owner) o;
        return owner_id.equals(that.owner_id) && Objects.equals(name, that.name) && Objects.equals(mail, that.mail) && Objects.equals(password, that.password) && Objects.equals(bank_account, that.bank_account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner_id, name, mail, password, bank_account);
    }
}