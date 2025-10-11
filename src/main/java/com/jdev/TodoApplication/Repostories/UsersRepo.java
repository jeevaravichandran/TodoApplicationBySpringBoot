package com.jdev.TodoApplication.Repostories;

import com.jdev.TodoApplication.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);
    boolean existsByUsername(String username);
}
