package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.tomcat.util.bcel.Const;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

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
//    @JsonIgnore
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