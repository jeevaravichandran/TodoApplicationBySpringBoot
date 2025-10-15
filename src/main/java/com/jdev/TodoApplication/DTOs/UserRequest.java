package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username Cannot be Null")
    private String username;
    @Size(min = 8 , max = 18 , message = "Password must be between 8 to 18 Characters")
    private String password;
    @NotBlank(message = "Name Cannot be Null")
    private String name;
    @Email(message = "Enter a valid Email ID")
    private String email;

}
