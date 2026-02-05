package com.jdev.TodoApplication.Services;

import com.jdev.TodoApplication.Models.CustomUserDetails;
import com.jdev.TodoApplication.Models.Users;
import com.jdev.TodoApplication.Repostories.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServices implements UserDetailsService {

    private final UsersRepo usersRepo;

    public CustomUserDetailsServices(UsersRepo usersRepo){
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepo.findByUsername(username);

        if(user == null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new CustomUserDetails(user);

    }
}
