package com.emilymenchu.projects.MovieManagement.persistence.service.impl;

import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import com.emilymenchu.projects.MovieManagement.persistence.repository.UserCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserCrudRepository crudRepository;

    @Autowired
    public UserServiceImpl(UserCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return crudRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAllByName(String name) {
        return crudRepository.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return crudRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("[user: " + username + "]"));
    }

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public User update(String username, User user) {
        User oldUser = this.findByUsername(username);
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());
        return crudRepository.save(oldUser);
    }

    @Override
    public void deleteByUsername(String username) {
        if (crudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user: " + username + "]");
        }
    }

    @Override
    public void deleteAll() {
        crudRepository.deleteAll();
        throw new RuntimeException();
    }
}