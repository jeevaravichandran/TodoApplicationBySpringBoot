package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank(message = "Invalid Username")
    private String username;
    @NotBlank(message = "Invalid Password")
    private String password;
}
