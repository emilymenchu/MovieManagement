package com.emilymenchu.projects.MovieManagement.mapper;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovieStatistic;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUserStatistic;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;

import java.util.List;

public class MovieMapper {

    public static GetMovieStatistic toGetMovieStatistic(Movie entity, Double averageRating, Integer minRating, Integer maxRating, Integer totalRatings) {
        if (entity == null) return null;
        return new GetMovieStatistic(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                totalRatings != null ? totalRatings : 0,
                entity.getReleaseYear(),
                averageRating != null ? averageRating : 0,
                minRating != null ? minRating : 0,
                maxRating != null ? maxRating : 0
        );
    }

    public static GetMovie toGetDto(Movie entity) {
        if (entity == null) return null;
        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                RatingMapper.toGetMovieRatingDtoList(entity.getRatings()),
                entity.getRatings() != null ? entity.getRatings().size() : 0
        );
    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(MovieMapper::toGetDto)
                .toList();
    }

    public static Movie toEntity(SaveMovie dto) {
        if (dto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(dto.title());
        newMovie.setDirector(dto.director());
        newMovie.setReleaseYear(dto.releaseYear());
        newMovie.setGenre(dto.genre());

        return newMovie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie saveDto) {
        if (oldMovie == null || saveDto == null) return;

        oldMovie.setGenre(saveDto.genre());
        oldMovie.setReleaseYear(saveDto.releaseYear());
        oldMovie.setTitle(saveDto.title());
        oldMovie.setDirector(saveDto.director());
    }
}