package com.jdev.TodoApplication.TypeConversion;

import com.jdev.TodoApplication.DTOs.TodoRequest;
import com.jdev.TodoApplication.DTOs.UserRegisterRequest;
import com.jdev.TodoApplication.Models.Todo;
import com.jdev.TodoApplication.Models.Users;
import org.springframework.security.core.context.SecurityContextHolder;

public class DtoIntoEntity {
    public static Users userRegisterRequestIntoUserEntity(UserRegisterRequest userRegisterRequest){
        Users newUser = new Users();
        newUser.setUsername(userRegisterRequest.getUsername());
        newUser.setEmail(userRegisterRequest.getEmail());
        newUser.setName(userRegisterRequest.getName());
        newUser.setRole("USER");
        return newUser;
    }

    public static Todo todoRequestIntoTodo(TodoRequest todoRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo newTodo = new Todo();
        newTodo.setName(todoRequest.getName());
        newTodo.setCompleted(todoRequest.getCompleted());
        newTodo.setUsername(username);
        return newTodo;
    }
}
