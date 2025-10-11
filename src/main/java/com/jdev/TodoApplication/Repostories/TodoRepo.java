package com.jdev.TodoApplication.Repostories;

import com.jdev.TodoApplication.Models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepo extends JpaRepository<Todo,Integer> {

    List<Todo> findByUsername(String username);
}
