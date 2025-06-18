package com.emilymenchu.projects.MovieManagement.persistence.repository;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findByMovieId(Long movieId, Pageable pageable);
    Page<Rating> findByUserUsername(String username, Pageable pageable);

    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = :username")
    Page<Rating> findByUsername(String username, Pageable pageable);

    boolean existsByMovieIdAndUserUsername(Long movieId, String username);

    @Query("select r.id from Rating r join r.user u where r.movieId = :movieId and u.username = :username")
    Long getRatingIdByMovieIdAndUserUsername(Long movieId, String username);
}