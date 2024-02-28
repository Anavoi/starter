package com.example.starter.converter;

import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserDto2UserEntity extends Converter<UserDto, UserEntity> {
    @Override
    UserEntity convert(@NotNull UserDto userDto);
}