package com.emilymenchu.projects.MovieManagement.controller;

import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import com.emilymenchu.projects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}