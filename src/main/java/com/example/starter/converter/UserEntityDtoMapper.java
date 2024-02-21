package com.example.starter.converter;

import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityDtoMapper {

    UserDto entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(UserDto userDto);
}