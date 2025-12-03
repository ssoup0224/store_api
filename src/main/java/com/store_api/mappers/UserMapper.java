package com.store_api.mappers;

import com.store_api.dtos.UserDto;
import com.store_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto.UserInfo toDto(User user);
    User toEntity(UserDto.RegisterUserRequest request);

    void update(UserDto.UpdateUserRequest request, @MappingTarget User user);
}

