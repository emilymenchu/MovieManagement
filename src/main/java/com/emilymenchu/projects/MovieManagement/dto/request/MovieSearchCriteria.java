package com.emilymenchu.projects.MovieManagement.dto.request;

import com.emilymenchu.projects.MovieManagement.util.MovieGenre;

import java.io.Serializable;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) implements Serializable {}