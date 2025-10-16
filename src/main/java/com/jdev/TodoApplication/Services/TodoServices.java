package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.TodoRequest;
import com.jdev.TodoApplication.DTOs.UpdatePasswordRequest;
import com.jdev.TodoApplication.DTOs.UpdateTodoRequest;
import com.jdev.TodoApplication.Models.Todo;
import com.jdev.TodoApplication.Repostories.TodoRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServices {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private JWTServices jwtServices;

    public Todo createTodo(TodoRequest todoRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setUsername(username);
        return todoRepo.save(todo);
    }


    public List<Todo> getAllTodos(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return todoRepo.findByUsername(username);
    }

    public List<Todo> findAllTodos() {
        return todoRepo.findAll();
    }


    public Todo updateTodo(UpdateTodoRequest updateTodoRequest) throws RuntimeException{
        String requestUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo existTodo = getTodoById(updateTodoRequest.getId());
        if(existTodo.getUsername().equals(requestUsername)){
            existTodo.setName(updateTodoRequest.getName());
            existTodo.setCompleted(updateTodoRequest.getCompleted());
            return todoRepo.save(existTodo);
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

    public void deleteTodoByUsername(String username){
        List<Todo> todoList = todoRepo.findByUsername(username);
        for(Todo todo : todoList){
            todoRepo.deleteById(todo.getId());
        }
    }

}
