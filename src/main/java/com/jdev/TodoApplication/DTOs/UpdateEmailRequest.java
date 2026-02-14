package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmailRequest {
    @NotBlank
    @Email(message = "Invalid Email")
    private String newEmail;
    @NotBlank(message = "Password cannot be empty")
    private String currentPassword;
}
