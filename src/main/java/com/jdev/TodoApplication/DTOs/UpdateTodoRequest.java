package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTodoRequest {
    @NotNull(message = "Invalid Id")
    private Integer id;
    @NotNull(message = "Completed cannot be null, Must be true/false")
    private Boolean completed;
}
