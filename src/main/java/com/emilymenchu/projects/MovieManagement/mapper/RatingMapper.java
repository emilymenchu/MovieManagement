package com.emilymenchu.projects.MovieManagement.mapper;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveRating;
import com.emilymenchu.projects.MovieManagement.dto.response.GetCompleteRating;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;

import java.util.List;

public class RatingMapper {

    public static GetCompleteRating toGetCompleteRatingDto(Rating entity) {
        if (entity == null) return null;
        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;
        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;
        return new GetCompleteRating(
                entity.getId(),
                entity.getMovieId(),
                movieTitle,
                username,
                entity.getRating()
        );
    }

    public static Rating toEntity(SaveRating dto, Long userId) {
        if (dto == null) return null;
        Rating entity = new Rating();
        entity.setMovieId(dto.movieId());
        entity.setUserId(userId);
        entity.setRating(dto.rating());
        return entity;
    }

    public static GetMovie.GetRating toGetMovieRatingDto(Rating entity) {
        if (entity == null) return null;
        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;
        return new GetMovie.GetRating(entity.getId(), entity.getRating(), username);
    }

    public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
        if (entity == null) return null;
        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;
        return new GetUser.GetRating(entity.getId(), movieTitle, entity.getMovieId(), entity.getRating());
    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;
        return entities.stream().map(RatingMapper::toGetMovieRatingDto).toList();
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;
        return entities.stream().map(RatingMapper::toGetUserRatingDto).toList();
    }

    public static void updateEntity(Rating oldRating, SaveRating saveDto, Long userId) {
        if (oldRating == null || saveDto == null) return;
        oldRating.setUserId(userId);
        oldRating.setMovieId(saveDto.movieId());
        oldRating.setRating(saveDto.rating());
    }
}