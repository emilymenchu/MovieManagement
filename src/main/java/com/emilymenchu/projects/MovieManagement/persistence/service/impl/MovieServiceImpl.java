package com.emilymenchu.projects.MovieManagement.persistence.service.impl;

import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.service.MovieService;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieCrudRepository movieCrudRepository;

    @Autowired
    public MovieServiceImpl(MovieCrudRepository movieCrudRepository) {
        this.movieCrudRepository = movieCrudRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAll() {
        return movieCrudRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieCrudRepository.findByTitleContaining(title);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByGenre(MovieGenre genre) {
        return movieCrudRepository.findByGenre(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAllByTitleAndGenre(String title, MovieGenre genre) {
        return movieCrudRepository.findByTitleContainingAndGenre(title, genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Movie findById(Long id) {
        return movieCrudRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[movie: "+ Long.toString(id) +"]"));
    }

    @Override
    public Movie save(Movie movie) {
        return movieCrudRepository.save(movie);
    }

    @Override
    public Movie update(Long id, Movie newMovie) {
        Movie oldMovie = this.findById(id);
        oldMovie.setGenre(newMovie.getGenre());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setDirector(newMovie.getDirector());
        return movieCrudRepository.save(oldMovie);
    }

    @Override
    public void delete(Long id) {
        Movie movie = this.findById(id);
        movieCrudRepository.delete(movie);
    }
}