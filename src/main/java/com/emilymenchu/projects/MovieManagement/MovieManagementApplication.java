package com.emilymenchu.projects.MovieManagement;

import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import com.emilymenchu.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.emilymenchu.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import com.emilymenchu.projects.MovieManagement.util.MovieGenre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class MovieManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner testRatingRepository(RatingCrudRepository repo) {
		return args -> {

		};
	}
}