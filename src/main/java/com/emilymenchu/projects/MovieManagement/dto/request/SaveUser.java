package com.emilymenchu.projects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveUser(
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveuser.username.pattern}")
        String username,
        @Size(max = 255, message = "{generic.size}")
        String name,
        @Size(min = 6, max = 255, message = "{generic.size}")
        String password,
        @JsonProperty(value = "password_repeated")
        @Size(min = 6, max = 255, message = "{generic.size}")
        String passwordRepeated
) implements Serializable {}