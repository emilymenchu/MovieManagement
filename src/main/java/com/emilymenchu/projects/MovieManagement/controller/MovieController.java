package com.emilymenchu.projects.MovieManagement.controller;

import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.persistence.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
//        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.findById(id));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> createOne(@RequestBody Movie movie, HttpServletRequest request) {
        Movie movieCreated = movieService.save(movie);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + movieCreated.getId());
        return ResponseEntity.created(newLocation).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            return ResponseEntity.ok(movieService.update(id, movie));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}