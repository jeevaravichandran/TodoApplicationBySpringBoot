package com.jdev.TodoApplication.Controllers;

import com.jdev.TodoApplication.DTOs.RequestDTOs.UserRequest;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Services.UsersServices;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersServices usersServices;

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "User Registered Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Username , User Already Exist")
    })
    public ResponseEntity<Users> registerUser(@Valid @RequestBody UserRequest request){
        try{
            Users user = usersServices.registerUser(request);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Login Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid Username/password, Please Check your Credentials")
    })
    public ResponseEntity<String> loginUser(@RequestBody Users user){
        return new ResponseEntity<>(usersServices.verifyUser(user),HttpStatus.OK);
    }
}
