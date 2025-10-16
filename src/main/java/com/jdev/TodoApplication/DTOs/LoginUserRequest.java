package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequest {
    @NotBlank(message = "Invalid Username")
    private String username;
    @NotBlank(message = "Invalid Password")
    private String password;
}
