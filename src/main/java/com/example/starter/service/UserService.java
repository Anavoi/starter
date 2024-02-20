package com.example.starter.service;

import com.example.starter.exception.UserNotFoundException;
import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import com.example.starter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity convertToEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        return userEntity;

    }
    public UserDto convertToDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        return userDto;

    }
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(convertToDto(userEntity));
        }
        return userDtoList;
    }

    public UserDto findOne(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        return convertToDto(userEntity);
    }
    public UserDto createUser(UserDto newDto){
        UserEntity userEntity = convertToEntity(newDto);
        return convertToDto(userRepository.save(userEntity));
    }
    public UserDto updateUser(UserDto userDto, Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        return convertToDto(userRepository.save(userEntity));
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}