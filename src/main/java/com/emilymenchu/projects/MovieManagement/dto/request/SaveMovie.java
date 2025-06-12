package com.emilymenchu.projects.MovieManagement.dto.request;

import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record SaveMovie(
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")
        int releaseYear
) implements Serializable {}