package com.emilymenchu.projects.MovieManagement.service.impl;

import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import com.emilymenchu.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import com.emilymenchu.projects.MovieManagement.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingCrudRepository crudRepository;

    @Autowired
    public RatingServiceImpl(RatingCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Rating> findAll(Pageable pageable) {
        return crudRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Rating> findAllByMovieId(Long movieId, Pageable pageable) {
        return crudRepository.findByMovieId(movieId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Rating> findAllByUsername(String username, Pageable pageable) {
        return crudRepository.findByUsername(username, pageable);
    }

    @Transactional(readOnly = true)
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