package com.emilymenchu.projects.MovieManagement.service;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveRating;
import com.emilymenchu.projects.MovieManagement.dto.response.GetCompleteRating;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {
    Page<GetCompleteRating> findAll(Pageable pageable);
    Page<GetCompleteRating> findAllByMovieId(Long movieId, Pageable pageable);
    Page<GetCompleteRating> findAllByUsername(String username, Pageable pageable);

    GetCompleteRating findById(Long id);
    Rating findEntityById(Long id);
    GetCompleteRating save(SaveRating rating);
    GetCompleteRating update(Long id, SaveRating rating);
    void delete(Long id);
}