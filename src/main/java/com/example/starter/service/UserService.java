package com.example.starter.service;

import com.example.starter.converter.UserEntityDtoMapper;
import com.example.starter.exception.UserNotFoundException;
import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import com.example.starter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityDtoMapper userEntityDtoMapper;

    public List<UserDto> getAll() {
        final List<UserDto> userDtoList = new ArrayList<>();
        final List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(userEntityDtoMapper.entityToDto(userEntity));
        }
        return userDtoList;
    }

    public UserDto findOne(Long id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userEntityDtoMapper.entityToDto(userEntity);
    }

    public UserDto createUser(UserDto newDto) {
        final UserEntity userEntity = userEntityDtoMapper.dtoToEntity(newDto);
        return userEntityDtoMapper.entityToDto(userRepository.save(userEntity));
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        return userEntityDtoMapper.entityToDto(userRepository.save(userEntity));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}