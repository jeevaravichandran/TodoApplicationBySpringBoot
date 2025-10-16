package com.jdev.TodoApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTodoRequest {
    @NotNull(message = "Invalid Id")
    private Integer id;
    @NotBlank(message = "Name cannot but empty , fill up with any name")
    private String name;
    @NotNull(message = "Completed cannot be null, Must be true/false")
    private Boolean completed;
}
