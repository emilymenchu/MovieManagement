package com.emilymenchu.projects.MovieManagement.service.impl;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.mapper.UserMapper;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import com.emilymenchu.projects.MovieManagement.persistence.repository.UserCrudRepository;
import com.emilymenchu.projects.MovieManagement.service.UserService;
import com.emilymenchu.projects.MovieManagement.service.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public Page<GetUser> findAll(String name, Pageable pageable) {
        return crudRepository.findByNameContaining(name, pageable).map(UserMapper::toGetUserDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetUser findByUsername(String username) {
        return UserMapper.toGetUserDto(this.findEntityByUsername(username));
    }


    @Transactional(readOnly = true)
    public User findEntityByUsername(String username) {
        return crudRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("[user: " + username + "]"));
    }

    @Override
    public GetUser save(SaveUser user) {
        PasswordValidator.validatePassword(user.password(), user.passwordRepeated());
        return UserMapper.toGetUserDto(crudRepository.save(UserMapper.toEntity(user)));
    }

    @Override
    public GetUser update(String username, SaveUser user) {
        PasswordValidator.validatePassword(user.password(), user.passwordRepeated());
        User oldUser = this.findEntityByUsername(username);
        UserMapper.updateEntity(oldUser, user);
        return UserMapper.toGetUserDto(crudRepository.save(oldUser));
    }

    @Override
    public void deleteByUsername(String username) {
        if (crudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user: " + username + "]");
        }
    }
}