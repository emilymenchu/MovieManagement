package com.emilymenchu.projects.MovieManagement.dto.request;

import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveMovie(
        @NotNull(message = "{generic.notnull}")
        @NotBlank(message = "{generic.notblank}")
        @Size(min = 4, max = 255, message = "{generic.size}")
        String title,
        @NotBlank(message = "{generic.notblank}")
        @Size(min = 4, max = 255, message = "{generic.size}")
        String director,
        MovieGenre genre,
        @Min(value = 1900, message = "{generic.min}")
        @JsonProperty(value = "release_year")
        int releaseYear
) implements Serializable {}