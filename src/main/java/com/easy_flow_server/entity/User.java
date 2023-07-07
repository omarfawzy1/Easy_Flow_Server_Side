package com.easy_flow_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(indexes = {@Index(columnList = "username")})
public abstract class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(nullable = false)
    protected String password;
    @Column(nullable = false)
    protected boolean active = true; // TODO set to false by default until gmail verification
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String roles = "";
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String permissions = "";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public List<String> getPermessionList() {
        if (!this.permissions.isEmpty())
            return Arrays.asList(this.permissions.split(","));
        return new ArrayList<>();
    }
    public List<String> getRoleList() {
        if (!this.roles.isEmpty())
            return Arrays.asList(this.roles.split(","));
        return new ArrayList<>();
    }

}