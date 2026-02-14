package com.jdev.TodoApplication.Controllers;

import com.jdev.TodoApplication.DTOs.*;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Services.UsersServices;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersServices usersServices;

    public UsersController(UsersServices usersServices){
        this.usersServices = usersServices;
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201" , description = "User Registered Successfully"),
            @ApiResponse(responseCode = "400", description = "Username Already Exist")
    })
    public ResponseEntity<Users> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        try{
            Users newUser = usersServices.registerUser(userRegisterRequest);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "Login Successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid Username/password")
    })
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest){
        try{
            String token = usersServices.verifyUser(userLoginRequest);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Exists", HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved Successfully")
    })
    public ResponseEntity<Users> getAccount(){
        return new ResponseEntity<>(usersServices.getAccount(), HttpStatus.OK);
    }

    @PutMapping("/account/update/email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Email Updated Successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong Password")
    })
    public ResponseEntity<String> updateUserEmail(@Valid @RequestBody UpdateEmailRequest updateEmailRequest){
        try{
            usersServices.updateUserEmail(updateEmailRequest);
            return new ResponseEntity<>("Email Updated Succeessfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Wrong Password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/account/update/password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password Updated Successfully"),
            @ApiResponse(responseCode = "406", description = "Current password is Wrong, Please enter the Correct Password and try again")
    })
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        try{
            usersServices.updatePassword(updatePasswordRequest.getNewPassword(), updatePasswordRequest.getCurrentPassword());
            return new ResponseEntity<>("Password Updated Successfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>("Current password is Wrong", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @DeleteMapping("/account/delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account Deleted Successfully"),
            @ApiResponse(responseCode = "401", description = "Wrong Password, Cannot delete the Account")
    })
    public ResponseEntity<String> deleteAccount(@Valid @RequestBody DeleteUserRequest deleteUserRequest){
        try{
            usersServices.deleteAccount(deleteUserRequest.getCurrentPassword());
            return new ResponseEntity<>("Account Deleted Successfully", HttpStatus.ACCEPTED);
        } catch (RuntimeException exception){
            return new ResponseEntity<>("Wrong password, Cannot delete the Account", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Users Retrieved Successfully"),
            @ApiResponse(responseCode = "401", description = "Admins only can See All Users")
    })
    public ResponseEntity<List<Users>> get(){
        try{
            List<Users> usersList = usersServices.getAllUsers();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202" , description = "User Account Deleted Successfully"),
            @ApiResponse(responseCode = "401", description = "Admin only access this method"),
            @ApiResponse(responseCode = "404", description = "User Not Exist"),
            @ApiResponse(responseCode = "400", description = "Wrong Password")
    })
    public ResponseEntity<String> delete(@PathVariable Integer id, @Valid @RequestBody DeleteUserRequest deleteUserRequest){
        try{
            usersServices.deleteUser(id, deleteUserRequest);
            return new ResponseEntity<>("User Account Deleted Successfully", HttpStatus.ACCEPTED);
        } catch (UnsupportedOperationException unsupportedOperationException){
            return new ResponseEntity<>("Admin Can only access this feature", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException usernameNotFoundException){
            return new ResponseEntity<>("User Not Exist", HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Wrong Password", HttpStatus.BAD_REQUEST);
        }
    }




}
