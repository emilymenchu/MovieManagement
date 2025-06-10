package com.emilymenchu.projects.MovieManagement.persistence.service;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGenre(MovieGenre genre);
    List<Movie> findAllByTitleAndGenre(String title, MovieGenre genre);
    Movie findById(Long id);
    Movie save(Movie movie);
    Movie update(Long id, Movie movie);
    void delete(Long id);
}