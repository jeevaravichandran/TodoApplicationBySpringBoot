package com.jdev.TodoApplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
}
