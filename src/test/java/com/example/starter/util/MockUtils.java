package com.example.starter.util;

import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class MockUtils {
    private final static AtomicLong ID_GENERATOR = new AtomicLong(1);
    public final static FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    public static UserEntity makeFakeUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(fakeValuesService.letterify("??????"));
        userEntity.setEmail(fakeValuesService.bothify("???????@gmail.com"));
        userEntity.setPassword(fakeValuesService.bothify("???????"));
        return userEntity;
    }

    public static UserDto makeUserDtoFromUserEntity(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userDto.getPassword());
        return userDto;
    }

    public static UserDto makeFakeUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(ID_GENERATOR.getAndIncrement());
        userDto.setName(fakeValuesService.letterify("??????"));
        userDto.setEmail(fakeValuesService.bothify("???????@gmail.com"));
        userDto.setPassword(fakeValuesService.bothify("???????"));
        return userDto;
    }

    public static UserDto makeFakeUserDtoNotValidEmail() {
        UserDto userDto = new UserDto();
        userDto.setId(ID_GENERATOR.getAndIncrement());
        userDto.setName(fakeValuesService.letterify("??????"));
        userDto.setEmail(fakeValuesService.bothify("???????gmail.com"));
        userDto.setPassword(fakeValuesService.bothify("???????"));
        return userDto;
    }

}
