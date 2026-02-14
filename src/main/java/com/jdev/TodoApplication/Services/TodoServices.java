package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.TodoRequest;
import com.jdev.TodoApplication.DTOs.UpdateTodoRequest;
import com.jdev.TodoApplication.Models.Todo;
import com.jdev.TodoApplication.Repostories.TodoRepo;
import com.jdev.TodoApplication.TypeConversion.DtoIntoEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServices {

    private final TodoRepo todoRepo;

    public TodoServices(TodoRepo todoRepo){
        this.todoRepo = todoRepo;
    }

    public Todo createTodo(TodoRequest todoRequest){
        Todo newTodo = DtoIntoEntity.todoRequestIntoTodo(todoRequest);
        return todoRepo.save(newTodo);
    }


    public List<Todo> getAllTodos(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return todoRepo.findByUsername(username);
    }

    public Todo updateTodo(UpdateTodoRequest updateTodoRequest){
        String requestUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Todo existTodo = getTodoById(updateTodoRequest.getId());
        if(existTodo.getUsername().equals(requestUsername)){
            existTodo.setCompleted(updateTodoRequest.getCompleted());
            return todoRepo.save(existTodo);
        }
        else{
            throw new RuntimeException("Not Found");
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
               throw new RuntimeException("Not Found");
           }
       } catch (Exception exception){
           throw new RuntimeException("Not Found");
       }
    }

    public void deleteTodoByUsername(String username){
        List<Todo> todoList = todoRepo.findByUsername(username);
        for(Todo todo : todoList){
            todoRepo.deleteById(todo.getId());
        }
    }
}
