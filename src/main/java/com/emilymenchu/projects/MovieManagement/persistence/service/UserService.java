package com.emilymenchu.projects.MovieManagement.persistence.service;

import com.emilymenchu.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findAllByName(String name);
    User findByUsername(String username);
    User save(User user);
    User update(String username, User user);
    void deleteByUsername(String username);
    void deleteAll();
}