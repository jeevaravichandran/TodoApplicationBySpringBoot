package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.DTOs.RequestDTOs.UserRequest;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Repostories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsersServices {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
     private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(UserRequest request){
        if(usersRepo.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already Exist, Try other name");
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        return usersRepo.save(user);
    }

    public String verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtServices.generateToken(user.getUsername()) ;
        }
        return "Invalid Account ! Please Create an account before login!!!";
    }

}
