package com.jdev.TodoApplication.Controllers;

import com.jdev.TodoApplication.DTOs.UpdatePasswordRequest;
import com.jdev.TodoApplication.DTOs.UpdateUserRequest;
import com.jdev.TodoApplication.DTOs.UserRequest;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Services.UsersServices;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Users Retrieved Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Request, Admins only can access the endpoint")
    })
    public ResponseEntity<List<Users>> get(){
        try{
            List<Users> usersList = usersServices.getAllUsers();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved Successfully")
    })
    public ResponseEntity<Users> getAccount(){
        return new ResponseEntity<>(usersServices.getAccount(), HttpStatus.OK);
    }

    @PutMapping("/account/update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Name and Email Updated Successfully")
    })
    public ResponseEntity<Users> updateUser(@Valid @RequestBody UpdateUserRequest request){
        return new ResponseEntity<>(usersServices.updateUser(request), HttpStatus.ACCEPTED);
    }

    @PutMapping("/account/update/password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password Updated Successfully"),
            @ApiResponse(responseCode = "406", description = "Your Old password is Wrong, Please enter the Correct Password and try again")
    })
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest request){
        try{
            usersServices.updatePassword(request.getOldPassword(), request.getNewPassword());
            return new ResponseEntity<>("Password Updated Successfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>("Your Old password is Wrong, Please enter the Correct Password and try again", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "User Account Deleted Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid Request")
    })
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try{
            usersServices.deleteUser(id);
            return new ResponseEntity<>("User Account Deleted Successfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>("Invalid Request , Cannot Delete User", HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/account/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account Deleted Successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong Password, Cannot delete the Account")
    })
    public ResponseEntity<String> deleteAccount(@RequestBody Users users){
        boolean isDeleted = usersServices.deleteAccount(users.getPassword());
        if(isDeleted){
            return new ResponseEntity<>("Account Deleted Successfully", HttpStatus.ACCEPTED);
        } else{
            return new ResponseEntity<>("Wrong password, Cannot delete the Account", HttpStatus.UNAUTHORIZED);
        }
    }

}
