package com.emilymenchu.projects.MovieManagement.controller;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Page<GetUser>> findAll(@RequestParam(required = false) String name, Pageable pageable){
        return ResponseEntity.ok(userService.findAll(name, pageable));
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetUser> findByUsername(@PathVariable String username) {
            return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<GetUser> save(@RequestBody @Valid SaveUser user, HttpServletRequest request) {
        GetUser userCreated = userService.save(user);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + userCreated.username());
        return ResponseEntity.created(newLocation).body(userCreated);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GetUser> update(@PathVariable String username, @RequestBody @Valid SaveUser user) {
            return ResponseEntity.ok(userService.update(username, user));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
            userService.deleteByUsername(username);
            return ResponseEntity.noContent().build();
    }
}