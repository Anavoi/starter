package com.example.starter.service;

import com.example.starter.exception.UserNotFoundException;
import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import com.example.starter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConverterService converterService;

    public List<UserDto> getAll() {
        final List<UserDto> userDtoList = new ArrayList<>();
        final List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(converterService.requiredConvert(userEntity, UserDto.class));
        }
        return userDtoList;
    }

    public UserDto findOne(Long id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return converterService.requiredConvert(userEntity, UserDto.class);
    }

    public UserDto createUser(UserDto newDto) {
        final UserEntity userEntity = converterService.requiredConvert(newDto, UserEntity.class);
        return converterService.requiredConvert(userRepository.save(Objects.requireNonNull(userEntity)), UserDto.class);
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        return converterService.requiredConvert(userRepository.save(userEntity), UserDto.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}