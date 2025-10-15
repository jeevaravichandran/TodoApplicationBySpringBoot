package com.jdev.TodoApplication.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Boolean completed;
    @JsonIgnore
    private String username;
}
