package com.emilymenchu.projects.MovieManagement.dto.response;

import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record GetMovie(
        long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")
        int releaseYear,
        List<GetRating> ratings,
        @JsonProperty("total_ratings") int totalRatings
) implements Serializable {
    public static record GetRating(
            long id,
            int rating,
            String username
    ) implements Serializable{}
}