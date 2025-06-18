package com.emilymenchu.projects.MovieManagement.service.impl;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveRating;
import com.emilymenchu.projects.MovieManagement.dto.response.GetCompleteRating;
import com.emilymenchu.projects.MovieManagement.exception.DuplicateRatingException;
import com.emilymenchu.projects.MovieManagement.exception.ObjectNotFoundException;
import com.emilymenchu.projects.MovieManagement.mapper.RatingMapper;
import com.emilymenchu.projects.MovieManagement.persistence.entity.Rating;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;
import com.emilymenchu.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import com.emilymenchu.projects.MovieManagement.service.RatingService;
import com.emilymenchu.projects.MovieManagement.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingCrudRepository crudRepository;
    private final UserService userService;
    private final EntityManager entityManager;

    @Autowired
    public RatingServiceImpl(RatingCrudRepository crudRepository, UserService userService, EntityManager entityManager) {
        this.crudRepository = crudRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return crudRepository.findAll(pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAllByMovieId(Long movieId, Pageable pageable) {
        return crudRepository.findByMovieId(movieId, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findAllByUsername(String username, Pageable pageable) {
        return crudRepository.findByUsername(username, pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetCompleteRating findById(Long id) {
        return RatingMapper.toGetCompleteRatingDto(this.findEntityById(id));
    }

    @Override
    public Rating findEntityById(Long id) {
        return crudRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[rating: " + Long.toString(id) + "]"));
    }

    @Override
    public GetCompleteRating save(SaveRating save) {
        if (crudRepository.existsByMovieIdAndUserUsername(save.movieId(), save.username())){
            return this.update(crudRepository.getRatingIdByMovieIdAndUserUsername(save.movieId(), save.username()), save);
        }
        User user = userService.findEntityByUsername(save.username());
        Rating rating = RatingMapper.toEntity(save, user.getId());
        crudRepository.save(rating);
        entityManager.detach(rating);
        return crudRepository.findById(rating.getId()).map(RatingMapper::toGetCompleteRatingDto).get();
    }

    @Override
    public GetCompleteRating update(Long id, SaveRating saveDto) {
        User user = userService.findEntityByUsername(saveDto.username());
        Rating oldRating = this.findEntityById(id);
        RatingMapper.updateEntity(oldRating, saveDto, user.getId());
        return RatingMapper.toGetCompleteRatingDto(crudRepository.save(oldRating));
    }

    @Override
    public void delete(Long id) {
        if (crudRepository.existsById(id)) {
            crudRepository.deleteById(id);
            return;
        }
        throw new ObjectNotFoundException("[rating: " + Long.toString(id) + "]");
    }
}