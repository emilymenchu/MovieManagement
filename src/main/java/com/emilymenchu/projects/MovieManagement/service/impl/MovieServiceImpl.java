package com.emilymenchu.projects.MovieManagement.service.impl;

import com.emilymenchu.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovieStatistic;
import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.mapper.MovieMapper;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.repository.RatingCrudRepository;
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
    private final RatingCrudRepository ratingCrudRepository;

    @Autowired
    public MovieServiceImpl(MovieCrudRepository movieCrudRepository, RatingCrudRepository ratingCrudRepository) {
        this.movieCrudRepository = movieCrudRepository;
        this.ratingCrudRepository = ratingCrudRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable) {
        FindAllMoviesSpecification specification = new FindAllMoviesSpecification(searchCriteria);
        return movieCrudRepository.findAll(specification, pageable).map(MovieMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetMovieStatistic findById(Long id) {
        Double averageRating = ratingCrudRepository.getAverageRatingByMovie(id);
        Integer minRating = ratingCrudRepository.getMinRatingByMovie(id);
        Integer maxRating = ratingCrudRepository.getMaxRatingByMovie(id);
        Integer totalRatings = ratingCrudRepository.countByMovieId(id);
        return MovieMapper.toGetMovieStatistic(this.findEntityById(id), averageRating, minRating, maxRating, totalRatings);
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