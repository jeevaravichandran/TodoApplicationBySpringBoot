package com.jdev.TodoApplication.Controllers;

import com.jdev.TodoApplication.DTOs.TodoRequest;
import com.jdev.TodoApplication.DTOs.UpdateTodoRequest;
import com.jdev.TodoApplication.Models.Todo;
import com.jdev.TodoApplication.Services.TodoServices;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoServices todoServices;
    public TodoController(TodoServices todoServices){
        this.todoServices = todoServices;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201" , description = "Todo Created Successfully")
    })
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest todoRequest){
        return new ResponseEntity<>(todoServices.createTodo(todoRequest), HttpStatus.CREATED);
    }


    @GetMapping("/get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Todos Retrieved Successfully")
    })
    public ResponseEntity<List<Todo>> getAllTodos(){
        return new ResponseEntity<>(todoServices.getAllTodos(),HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "Todo Updated Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Todo")
    })
    public ResponseEntity<Todo> updateTodo(@Valid @RequestBody UpdateTodoRequest updateTodoRequest){
        try {
            Todo updatedTodo = todoServices.updateTodo(updateTodoRequest);
            return new ResponseEntity<>(updatedTodo, HttpStatus.ACCEPTED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "Todo Deleted Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Todo Cannot be deleted")
    })
    public ResponseEntity<String> deleteTodo(@PathVariable int id){
        try{
            todoServices.deleteTodo(id);
            return new ResponseEntity<>("Todo Deleted Successfully", HttpStatus.ACCEPTED);
        } catch (Exception exception){
            return new ResponseEntity<>("Todo Not Exist", HttpStatus.NOT_FOUND);
        }
    }

}
