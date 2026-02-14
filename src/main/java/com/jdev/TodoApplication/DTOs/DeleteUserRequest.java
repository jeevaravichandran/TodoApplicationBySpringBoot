package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUserRequest {
    @NotBlank(message = "Invalid Password")
    private String currentPassword;
}
