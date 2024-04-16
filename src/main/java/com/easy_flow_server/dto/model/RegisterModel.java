package com.easy_flow_server.dto.model;

import com.easy_flow_server.entity.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {
    @NotBlank(message = "First name is mandatory")
    @Length(max = 256)
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Length(max = 126)
    private String lastName;
    @NotBlank(message = "User name is mandatory")
    @Length(max = 256)
    private String username;
    @Email
    @NotBlank(message = "User email is mandatory")
    @Length(max = 256)
    private String email;
    @Length(max = 256)
    private String city;
    @NotBlank(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Length(min = 8, max = 64, message = "Password length should be at least 8")
    private String password;
    @NotBlank(message = "Phone is mandatory")
    @Length(min = 11, max = 11, message = "phone number must be exactly 11 number")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "phone number consists of numbers only")
    private String phoneNumber;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDay;

}

