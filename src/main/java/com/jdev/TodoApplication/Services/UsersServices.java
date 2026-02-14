package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.DeleteUserRequest;
import com.jdev.TodoApplication.DTOs.UserLoginRequest;
import com.jdev.TodoApplication.DTOs.UpdateEmailRequest;
import com.jdev.TodoApplication.DTOs.UserRegisterRequest;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Repostories.UsersRepo;
import com.jdev.TodoApplication.TypeConversion.DtoIntoEntity;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsersServices {
    private final UsersRepo usersRepo;
    private final JWTServices jwtServices;
    private final AuthenticationManager authenticationManager;
    private final TodoServices todoServices;
    private final BCryptPasswordEncoder encoder;

    public UsersServices(UsersRepo usersRepo, JWTServices jwtServices,
                         AuthenticationManager authenticationManager,
                         TodoServices todoServices){
        this.usersRepo = usersRepo;
        this.jwtServices = jwtServices;
        this.authenticationManager = authenticationManager;
        this.todoServices = todoServices;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public Users registerUser(UserRegisterRequest userRegisterRequest){
        if(usersRepo.existsByUsername(userRegisterRequest.getUsername())){
            throw new RuntimeException("Username Exists");
        }
        Users newUser = DtoIntoEntity.userRegisterRequestIntoUserEntity(userRegisterRequest);
        newUser.setPassword(encoder.encode(userRegisterRequest.getPassword()));
        return usersRepo.save(newUser);
    }

    public String verifyUser(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        if(authentication.isAuthenticated()) {
                return jwtServices.generateToken(userLoginRequest.getUsername());
        }
        return "User Not Exist!";
    }

    public List<Users> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        if(role.equals("ADMIN")){
           return usersRepo.findAll();
        }
        throw new RuntimeException("Invalid Request");
    }

    public Users getById(Integer id){
        return usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User not fount"));
    }

    @Transactional
    public void deleteUser(Integer id, DeleteUserRequest deleteUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users requestedUser = usersRepo.findByUsername(username);
        boolean userExist = usersRepo.existsUserById(id);
        if(!userExist){
            throw new UsernameNotFoundException("User Not Exist");
        }
        else if(requestedUser.getRole().equals("ADMIN")){
            if(encoder.matches(deleteUserRequest.getCurrentPassword(), requestedUser.getPassword())){
                Users user = getById(id);
                todoServices.deleteTodoByUsername(user.getUsername());
                usersRepo.deleteById(id);
            }
            else{
                throw new RuntimeException("Wrong Password");
            }

        }
        else {
            throw new UnsupportedOperationException("Admin Can Only access this method");
        }
    }

    public Users getAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepo.findByUsername(username);
    }

    @Transactional
    public void deleteAccount(String password){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username);
        if(encoder.matches(password, user.getPassword())){
            todoServices.deleteTodoByUsername(username);
            usersRepo.deleteById(user.getId());
        }
        else{
            throw new RuntimeException("Invalid Password");
        }
    }

    public void updateUserEmail(UpdateEmailRequest updateEmailRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users existUser = usersRepo.findByUsername(username);
        if(encoder.matches(updateEmailRequest.getCurrentPassword(), existUser.getPassword())){
            existUser.setEmail(updateEmailRequest.getNewEmail());
            usersRepo.save(existUser);
            return;
        }
        throw new RuntimeException("Wrong Password");
    }

    public void updatePassword(String newPassword, String currentPassword){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username);
        if(encoder.matches(currentPassword, user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            usersRepo.save(user);
            return;
        }
        throw new RuntimeException("Wrong Password");
    }
}
