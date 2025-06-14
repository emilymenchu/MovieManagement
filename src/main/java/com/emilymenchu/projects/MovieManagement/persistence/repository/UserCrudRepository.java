package com.emilymenchu.projects.MovieManagement.persistence.repository;

import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);
    Optional<User> findByUsername(String username);
    @Modifying
    @Transactional
    int deleteByUsername(String username);
}