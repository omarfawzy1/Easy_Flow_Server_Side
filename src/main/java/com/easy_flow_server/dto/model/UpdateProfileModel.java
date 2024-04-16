package com.easy_flow_server.dto.model;

import com.easy_flow_server.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileModel {
    @NotBlank(message = "First name is mandatory")
    @Length(max = 256)
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Length(max = 256)
    private String lastName;
    @Email
    @NotBlank(message = "User email is mandatory")
    @Length(max = 256)
    private String email;
    @NotBlank(message = "Phone is mandatory")
    @Length(min = 11, max = 11)
    @Pattern(regexp = "(^$|[0-9]{11})", message = "phone number consists of numbers only")
    private String phoneNumber;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
}
