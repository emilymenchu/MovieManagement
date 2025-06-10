package com.emilymenchu.projects.MovieManagement.persistence.service.impl;

import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import com.emilymenchu.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingCrudRepository crudRepository;

    @Autowired
    public RatingServiceImpl(RatingCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Rating> findAll() {
        return crudRepository.findAll();
    }

    @Override
    public List<Rating> findAllByMovieId(Long movieId) {
        return crudRepository.findByMovieId(movieId);
    }

    @Override
    public List<Rating> findAllByUsername(String username) {
        return crudRepository.findByUsername(username);
    }

    @Override
    public Rating findById(Long id) {
        return crudRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[rating: " + Long.toString(id) + "]"));
    }

    @Override
    public Rating save(Rating rating) {
        return crudRepository.save(rating);
    }

    @Override
    public Rating update(Long id, Rating rating) {
        Rating oldRating = this.findById(id);
        oldRating.setUserId(rating.getUserId());
        oldRating.setMovieId(rating.getMovieId());
        oldRating.setRating(rating.getRating());
        return crudRepository.save(oldRating);
    }

    @Override
    public void delete(Long id) {
        if (crudRepository.existsById(id)) {
            crudRepository.deleteById(id);
            return;
        }
        throw new ObjectNotFoundException("[rating: " + Long.toString(id) + "]");
    }
}