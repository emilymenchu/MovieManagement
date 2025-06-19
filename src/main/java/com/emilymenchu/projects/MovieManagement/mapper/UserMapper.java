package com.emilymenchu.projects.MovieManagement.mapper;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUserStatistic;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public class UserMapper {

    public static GetUserStatistic toGetUserStatistic(User entity, Double averageRating, Integer minRating, Integer maxRating, Integer totalRatings) {
        if (entity == null) return null;

        return new GetUserStatistic(
                entity.getUsername(),
                entity.getCreatedAt(),
                totalRatings != null ? totalRatings : 0,
                averageRating != null ? averageRating : 0,
                minRating != null ? minRating : 0,
                maxRating != null ? maxRating : 0
                );
    }


    public static GetUser toGetUserDto(User entity){
        if (entity == null) return null;
        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                RatingMapper.toGetUserRatingDtoList(entity.getRatings()),
                entity.getRatings() != null ? entity.getRatings().size() : 0
        );
    }

    public static List<GetUser> toGetUserList(List<User> entities) {
        if (entities == null) return null;
        return entities.stream().map(UserMapper::toGetUserDto).toList();
    }

    public static User toEntity(SaveUser dto) {
        if (dto == null) return null;
        User newUser = new User();
        newUser.setUsername(dto.username());
        newUser.setName(dto.name());
        newUser.setPassword(dto.password());
        return newUser;
    }

    public static void updateEntity(User oldUser, SaveUser user) {
        if (oldUser == null || user == null) return;

        oldUser.setName(user.name());
        oldUser.setPassword(user.username());
    }
}