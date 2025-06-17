package com.emilymenchu.projects.MovieManagement.persistence.specification;

import com.emilymenchu.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Movie;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {
    private MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(searchCriteria.title())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + this.searchCriteria.title().toLowerCase() + "%"));
        }

        if (searchCriteria.genres() != null && searchCriteria.genres().length > 0) {
            predicates.add(criteriaBuilder.in(root.get("genre")).value(Arrays.stream(this.searchCriteria.genres()).toList()));
        }

        if (searchCriteria.minReleaseYear() != null && searchCriteria.minReleaseYear() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear()));
        }

        if (searchCriteria.maxReleaseYear() != null && searchCriteria.maxReleaseYear() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxReleaseYear()));
        }

        if (searchCriteria.minAverageRating() != null && searchCriteria.minAverageRating() > 0) {
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Rating> subRoot = subquery.from(Rating.class);
            subquery.select(criteriaBuilder.avg(subRoot.get("rating")));
            subquery.where(criteriaBuilder.equal(root.get("id"), subRoot.get("movieId")));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(subquery, Double.valueOf(this.searchCriteria.minAverageRating())));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}