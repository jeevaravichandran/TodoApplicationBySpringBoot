package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.RequestDTOs.TodoRequest;
import com.jdev.TodoApplication.DTOs.ResponseDTOs.TodoResponse;
import com.jdev.TodoApplication.Models.Todo;
import com.jdev.TodoApplication.Repostories.TodoRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServices {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private ModelMapper modelMapper;

    public Todo createTodo(HttpServletRequest request, TodoRequest todoRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setUsername(username);
        return todoRepo.save(todo);
    }


    public List<TodoResponse> getAllTodos(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Todo> todoList = todoRepo.findByUsername(username);
         List<TodoResponse> todoResponseList = todoList.stream()
                .map(todo -> modelMapper.map(todo,TodoResponse.class))
                .collect(Collectors.toList());
         return todoResponseList;
    }

    public Todo updateTodo(Todo todo){
        String requestUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(todo.getUsername().equals(requestUsername)){
            return todoRepo.save(todo);
        }
        else{
            throw new RuntimeException("Invalid Todo Id , Please Check the Id");
        }
    }

    public Todo getTodoById(int id){
        return todoRepo.findById(id).orElseThrow(() -> new RuntimeException("Todo Not Found"));
    }

    public void deleteTodo(int id){
       try{
           Todo existTodo = getTodoById(id);
           String requesterUsername = SecurityContextHolder.getContext().getAuthentication().getName();
           if(requesterUsername.equals(existTodo.getUsername())){
               todoRepo.deleteById(id);
           }
           else{
               throw new EmptyResultDataAccessException(0);
           }
       } catch (Exception exception){
           throw new EmptyResultDataAccessException(0);
       }

    }

}
