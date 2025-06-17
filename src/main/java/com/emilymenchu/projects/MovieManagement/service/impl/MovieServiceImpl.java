package com.emilymenchu.projects.MovieManagement.service.impl;

import com.emilymenchu.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.mapper.MovieMapper;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.specification.FindAllMoviesSpecification;
import com.emilymenchu.projects.MovieManagement.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable) {
        FindAllMoviesSpecification specification = new FindAllMoviesSpecification(searchCriteria);
        return movieCrudRepository.findAll(specification, pageable).map(MovieMapper::toGetDto);
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