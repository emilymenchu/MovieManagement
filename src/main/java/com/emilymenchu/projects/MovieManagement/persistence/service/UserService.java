package com.emilymenchu.projects.MovieManagement.persistence.service;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {
    List<GetUser> findAll();
    List<GetUser> findAllByName(String name);
    GetUser findByUsername(String username);
    GetUser save(SaveUser user);
    GetUser update(String username, SaveUser user);
    void deleteByUsername(String username);
}