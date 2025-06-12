package com.emilymenchu.projects.MovieManagement.persistence.service;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {
    List<GetMovie> findAll();
    List<GetMovie> findAllByTitle(String title);
    List<GetMovie> findAllByGenre(MovieGenre genre);
    List<GetMovie> findAllByTitleAndGenre(String title, MovieGenre genre);
    GetMovie findById(Long id);
    GetMovie save(SaveMovie movie);
    GetMovie update(Long id, SaveMovie movie);
    void delete(Long id);
}