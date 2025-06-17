package com.emilymenchu.projects.MovieManagement.service;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {
    Page<Rating> findAll(Pageable pageable);
    Page<Rating> findAllByMovieId(Long movieId, Pageable pageable);
    Page<Rating> findAllByUsername(String username, Pageable pageable);

    Rating findById(Long id);
    Rating save(Rating rating);
    Rating update(Long id, Rating rating);
    void delete(Long id);
}