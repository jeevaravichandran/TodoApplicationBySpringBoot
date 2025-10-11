package com.jdev.TodoApplication.DTOs.RequestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoRequest {
    @NotBlank(message = "Todo must have any name")
    String name;
    @NotNull(message = "Todo Status must be filled, true/false")
    Boolean completed;
}
