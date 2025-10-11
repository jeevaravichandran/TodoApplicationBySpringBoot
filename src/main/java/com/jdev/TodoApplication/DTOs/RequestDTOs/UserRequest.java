package com.jdev.TodoApplication.DTOs.RequestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username Cannot be Null")
    String username;
    @NotBlank(message = "Password Cannot be Null")
    @Size(min = 8 , max = 18 , message = "Password must be between 8 to 18 Characters")
    String password;
}
