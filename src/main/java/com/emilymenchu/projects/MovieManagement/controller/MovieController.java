package com.emilymenchu.projects.MovieManagement.controller;

import com.emilymenchu.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.emilymenchu.projects.MovieManagement.dto.request.SaveMovie;
import com.emilymenchu.projects.MovieManagement.dto.response.GetMovie;
import com.emilymenchu.projects.MovieManagement.service.MovieService;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAll(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) MovieGenre[] genres,
                                                  @RequestParam(required = false) Integer minReleaseYear,
                                                  @RequestParam(required = false) Integer maxReleaseYear,
                                                  @RequestParam(required = false) Integer minAverageRating,
                                                  @PageableDefault Pageable pageable
                                                  ) {
//        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title, genres, minReleaseYear, maxReleaseYear, minAverageRating);
        return ResponseEntity.ok(movieService.findAll(searchCriteria, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMovie> findById(@PathVariable Long id) {
            return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(@RequestBody @Valid SaveMovie movie, HttpServletRequest request) {
        GetMovie movieCreated = movieService.save(movie);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + movieCreated.id());
        return ResponseEntity.created(newLocation).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMovie> update(@PathVariable Long id, @RequestBody @Valid SaveMovie movie) {
            return ResponseEntity.ok(movieService.update(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
            movieService.delete(id);
            return ResponseEntity.noContent().build();
    }
}
