package com.emilymenchu.projects.MovieManagement.mapper;

import com.emilymenchu.projects.MovieManagement.dto.request.SaveUser;
import com.emilymenchu.projects.MovieManagement.dto.response.GetUser;
import com.emilymenchu.projects.MovieManagement.persistence.entity.User;

import java.util.List;

public class UserMapper {
    public static GetUser toGetUserDto(User entity){
        if (entity == null) return null;
        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                RatingMapper.toGetUserRatingDtoList(entity.getRatings())
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