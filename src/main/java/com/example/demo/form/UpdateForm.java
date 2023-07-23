package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateForm {
    @NotNull(message = "ID cannot be null or empty")
    private Long id;
    @NotEmpty(message = "Username cannot be empty")
    private String username;

//    @NotEmpty(message = "Last name cannot be empty")
//    private String lastName;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;

//    @Pattern(regexp = "^\\d{11}$", message = "Invalid phone number")
//    private String phone;
//    private String address;

//    private String title;
//    private String bio;
}
