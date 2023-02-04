package com.example.easy_flow_backend.view;

import com.example.easy_flow_backend.entity.Gender;
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
    private String userName;
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Length(min = 8, max = 64)
    private String password;
    @NotBlank(message = "Phone is mandatory")
    @Length(min = 11, max = 11)
    @Pattern(regexp = "(^$|[0-9]{11})")
    private String phoneNum;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthDay;

}

