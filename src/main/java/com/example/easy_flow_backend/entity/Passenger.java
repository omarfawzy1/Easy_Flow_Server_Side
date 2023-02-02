package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Passenger extends User {

    @OneToOne
    @MapsId
    @JoinColumn(name = "wallet_id",referencedColumnName ="wallet_id")
    private Wallet wallet;

    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    private String type;
    private String city;
    @Column(nullable = false)
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day",nullable = false)
    private java.util.Date birthDay;

    public Passenger(){};

    public Passenger(String id, String firstName) {
        this.id=id;
        this.firstName = firstName;
    }
    public Passenger(String id, Wallet wallet, String firstName, String lastName, String phoneNumber, String type, String city, String gender, Date birthDay, String username, String password) {
        super(username, password);
        roles = "PASSENGER";
        this.id = id;
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
    }


    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet_fk) {
        this.wallet = wallet_fk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birth_day) {
        this.birthDay = birth_day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(wallet, passenger.wallet) && Objects.equals(firstName, passenger.firstName) && Objects.equals(lastName, passenger.lastName) && Objects.equals(phoneNumber, passenger.phoneNumber) && Objects.equals(type, passenger.type) && Objects.equals(city, passenger.city) && Objects.equals(gender, passenger.gender) && Objects.equals(birthDay, passenger.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wallet, firstName, lastName, phoneNumber, type, city, gender, birthDay);
    }
}
