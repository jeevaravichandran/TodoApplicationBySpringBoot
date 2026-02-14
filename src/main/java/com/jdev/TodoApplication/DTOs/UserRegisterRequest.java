package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "Name Cannot be Null")
    private String name;
    @NotBlank
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Username Cannot be Empty")
    private String username;
    @NotBlank
    @Size(min = 8 , max = 18 , message = "Password must be between 8 to 18 Characters")
    private String password;


}
