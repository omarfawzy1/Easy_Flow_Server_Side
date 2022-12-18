package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Passenger extends User {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="wallet_id")
    private Wallet wallet_fk;

    private String first_name;
    private String last_name;
    private String phone_number;
    private String type;
    private String city;
    private String gender;

    @Temporal(TemporalType.DATE)
    private java.util.Date birth_day;

    public Passenger(){};

    public Passenger(String id, String first_name) {
        this.id=id;
        this.first_name = first_name;
    }
    public Passenger(String passenger_id, Wallet wallet_fk, String first_name, String last_name, String phone_number, String type, String city, String gender, Date birth_day) {

        this.id = passenger_id;
        this.wallet_fk = wallet_fk;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.type = type;
        this.city = city;
        this.gender = gender;
        this.birth_day = birth_day;
    }


    public Wallet getWallet_fk() {
        return wallet_fk;
    }

    public void setWallet_fk(Wallet wallet_fk) {
        this.wallet_fk = wallet_fk;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(wallet_fk, passenger.wallet_fk) && Objects.equals(first_name, passenger.first_name) && Objects.equals(last_name, passenger.last_name) && Objects.equals(phone_number, passenger.phone_number) && Objects.equals(type, passenger.type) && Objects.equals(city, passenger.city) && Objects.equals(gender, passenger.gender) && Objects.equals(birth_day, passenger.birth_day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wallet_fk, first_name, last_name, phone_number, type, city, gender, birth_day);
    }
}
