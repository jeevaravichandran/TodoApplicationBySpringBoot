package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotNull(message = "New Password Cannot be Empty")
    @Size(min = 8, max = 18 , message = "Password must be between 8 to 18 Characters")
    private String newPassword;
    @NotBlank(message = "Please enter the old password to update the new password")
    private String currentPassword;
}
