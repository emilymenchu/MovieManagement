package com.emilymenchu.projects.MovieManagement.persistence.service.impl;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.mapper.MovieMapper;
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
    public List<GetMovie> findAll() {
        return MovieMapper.toGetDtoList(movieCrudRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByTitle(String title) {
        return MovieMapper.toGetDtoList(movieCrudRepository.findByTitleContaining(title));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByGenre(MovieGenre genre) {
        return MovieMapper.toGetDtoList(movieCrudRepository.findByGenre(genre));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetMovie> findAllByTitleAndGenre(String title, MovieGenre genre) {
        return MovieMapper.toGetDtoList(movieCrudRepository.findByTitleContainingAndGenre(title, genre));
    }

    @Transactional(readOnly = true)
    @Override
    public GetMovie findById(Long id) {
        return MovieMapper.toGetDto(this.findEntityById(id));
    }

    @Transactional(readOnly = true)
    private Movie findEntityById(Long id) {
        return movieCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[movie: "+ Long.toString(id) +"]"));
    }

    @Override
    public GetMovie save(SaveMovie movie) {
        return MovieMapper.toGetDto(movieCrudRepository.save(MovieMapper.toEntity(movie)));
    }

    @Override
    public GetMovie update(Long id, SaveMovie newMovie) {
        Movie oldMovie = this.findEntityById(id);
        MovieMapper.updateEntity(oldMovie, newMovie);
        return MovieMapper.toGetDto(movieCrudRepository.save(oldMovie));
    }

    @Override
    public void delete(Long id) {
        Movie movie = this.findEntityById(id);
        movieCrudRepository.delete(movie);
    }
}