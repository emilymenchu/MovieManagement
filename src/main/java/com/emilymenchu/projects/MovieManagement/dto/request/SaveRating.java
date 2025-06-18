package com.emilymenchu.projects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveRating(
        @NotNull(message = "{generic.notnull}")
        @Positive(message = "{generic.positive}")
        @JsonProperty("movie_id")
        Long movieId,
        @NotEmpty(message = "{generic.notempty}")
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveuser.username.pattern}")
        String username,

        @NotNull(message = "{generic.notnull}")
        @Min(value = 0, message = "{generic.min}")
        @Max(value = 5, message = "{generic.max}")
        Integer rating
) implements Serializable {}