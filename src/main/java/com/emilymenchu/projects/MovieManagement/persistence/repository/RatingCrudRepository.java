package com.emilymenchu.projects.MovieManagement.persistence.repository;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovieId(Long movieId);
    List<Rating> findByUserUsername(String username);

    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = :username")
    List<Rating> findByUsername(String username);
}