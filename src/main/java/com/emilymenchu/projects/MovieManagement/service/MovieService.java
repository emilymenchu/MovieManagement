package com.emilymenchu.projects.MovieManagement.service;

import com.emilymenchu.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovieStatistic;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable);
    GetMovieStatistic findById(Long id);
    GetMovie save(SaveMovie movie);
    GetMovie update(Long id, SaveMovie movie);
    void delete(Long id);
}