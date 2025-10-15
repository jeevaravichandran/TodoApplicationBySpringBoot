package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Name cannot be null")
    private String name;
    @Email(message = "Invalid Email")
    private String email;
}
