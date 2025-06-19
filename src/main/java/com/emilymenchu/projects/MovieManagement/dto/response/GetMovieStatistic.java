package com.emilymenchu.projects.MovieManagement.dto.response;

import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetMovieStatistic(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty("total_ratings")
        int totalRatings,
        @JsonProperty("release_year")
        int releaseYear,
        @JsonProperty("average_ratings")
        Double averageRating,
        @JsonProperty("lowest_rating")
        int lowestRating,
        @JsonProperty("highest_rating")
        int highestRating
) implements Serializable {
        public Double averageRating() {
                return Double.parseDouble(String.format("%1.2f", averageRating));
        }
}