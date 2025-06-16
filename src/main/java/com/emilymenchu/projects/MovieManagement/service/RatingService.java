package com.emilymenchu.projects.MovieManagement.service;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();
    List<Rating> findAllByMovieId(Long movieId);
    List<Rating> findAllByUsername(String username);

    Rating findById(Long id);
    Rating save(Rating rating);
    Rating update(Long id, Rating rating);
    void delete(Long id);
}