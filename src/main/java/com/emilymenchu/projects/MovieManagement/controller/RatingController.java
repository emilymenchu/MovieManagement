package com.emilymenchu.projects.MovieManagement.controller;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveRating;
import com.emilymenchu.projects.MovieManagement.dto.response.GetCompleteRating;
import com.emilymenchu.projects.MovieManagement.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> findAll(Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> findById(@PathVariable Long ratingId) {
        return ResponseEntity.ok(ratingService.findById(ratingId));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRating> save(@RequestBody @Valid SaveRating saveRating, HttpServletRequest request){
        GetCompleteRating ratingSaved = ratingService.save(saveRating);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + ratingSaved.ratingId());
        return ResponseEntity.created(newLocation).body(ratingSaved);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> update(@PathVariable Long ratingId, @RequestBody @Valid SaveRating saveRating) {
        return ResponseEntity.ok(ratingService.update(ratingId, saveRating));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> delete(@PathVariable Long ratingId) {
        ratingService.delete(ratingId);
        return ResponseEntity.noContent().build();
    }
}