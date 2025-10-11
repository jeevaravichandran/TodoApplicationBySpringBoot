package com.jdev.TodoApplication.DTOs.ResponseDTOs;

import lombok.Data;

@Data
public class TodoResponse {
    private Integer id;
    private String name;
    private Boolean completed;
}
