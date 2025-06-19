package com.emilymenchu.projects.MovieManagement.service;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUserStatistic;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.List;

public interface UserService {
    Page<GetUser> findAll(String name, Pageable pageable);
    /**
     * @param username
     * @throws {@link com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException} if the given username does not exist
     * @return
     * */
    GetUserStatistic findByUsername(String username);

    /**
     * @param username
     * @throws {@link com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException} if the given username does not exist
     * @return
     */
    User findEntityByUsername(String username);
    GetUser save(SaveUser user);
    GetUser update(String username, SaveUser user);
    void deleteByUsername(String username);
}