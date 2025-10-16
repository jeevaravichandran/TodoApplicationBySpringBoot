package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.UpdateUserRequest;
import com.jdev.TodoApplication.DTOs.UserRequest;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Repostories.UsersRepo;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsersServices {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
     private AuthenticationManager authenticationManager;

    @Autowired
    private TodoServices todoServices;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(UserRequest request){
        if(usersRepo.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already Exist, Try other name");
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole("USER");
        return usersRepo.save(user);
    }

    public String verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtServices.generateToken(user.getUsername()) ;
        }
        return "Invalid Account ! Please Create an account before login!!!";
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
    public void deleteUser(Integer id) throws RuntimeException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        boolean userExist = usersRepo.existsUserById(id);
        if(role.equals("ADMIN") && userExist){
            Users user = getById(id);
            todoServices.deleteTodoByUsername(user.getUsername());
            usersRepo.deleteById(id);
        }
        else {
            throw new RuntimeException("Invalid Request");
        }
    }

    public Users getAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepo.findByUsername(username);
    }

    @Transactional
    public boolean deleteAccount(String password) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username);
        if(encoder.matches(password, user.getPassword())){
            todoServices.deleteTodoByUsername(username);
            usersRepo.deleteById(user.getId());
            return true;
        }
        return false;
    }

    public Users updateUser(@Valid UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return usersRepo.save(user);
    }

    public void updatePassword(String oldPassword, String newPassword){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepo.findByUsername(username);
        if(encoder.matches(oldPassword, user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            usersRepo.save(user);
        }
        else{
            throw new RuntimeException("Wrong Password");
        }
    }
}
