package com.example.starter.service;

import com.example.starter.exception.UserNotFoundException;
import com.example.starter.model.dto.UserDto;
import com.example.starter.model.entity.UserEntity;
import com.example.starter.repository.UserRepository;
import com.example.starter.util.MockUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    public static List<UserEntity> userEntityList;

    @BeforeAll
    public static void initEntityList() {
        userEntityList = List.of(
                MockUtils.makeFakeUser(),
                MockUtils.makeFakeUser(),
                MockUtils.makeFakeUser());
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(userEntityList);
        final List<UserDto> userDtoList = userService.getAll();
        assertAll("Equality check",
                () -> assertEquals(userEntityList.getFirst().getId(), userDtoList.getFirst().getId()),
                () -> assertEquals(userEntityList.getFirst().getName(), userDtoList.getFirst().getName()),
                () -> assertEquals(userEntityList.getFirst().getEmail(), userDtoList.getFirst().getEmail()),
                () -> assertEquals(userEntityList.getFirst().getPassword(), userDtoList.getFirst().getPassword()));

        assertAll("Equality check",
                () -> assertEquals(userEntityList.getLast().getId(), userDtoList.getLast().getId()),
                () -> assertEquals(userEntityList.getLast().getName(), userDtoList.getLast().getName()),
                () -> assertEquals(userEntityList.getLast().getEmail(), userDtoList.getLast().getEmail()),
                () -> assertEquals(userEntityList.getLast().getPassword(), userDtoList.getLast().getPassword()));

        verify(userRepository).findAll();
    }

    @Test
    void testFindOneOk() {
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(userEntityList.get(1)));
        final UserDto userDto = userService.findOne(2L);
        assertAll("Equality check",
                () -> assertEquals(userEntityList.get(1).getId(), userDto.getId()),
                () -> assertEquals(userEntityList.get(1).getName(), userDto.getName()),
                () -> assertEquals(userEntityList.get(1).getEmail(), userDto.getEmail()),
                () -> assertEquals(userEntityList.get(1).getPassword(), userDto.getPassword()));
        verify(userRepository).findById(2L);
    }

    @Test
    void testFindOneUserNotFoundEx() {
        when(userRepository.findById(4L)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userService.findOne(4L));
        verify(userRepository).findById(4L);
    }

    @Test
    void testCreateUser() {
        final UserEntity userEntity = MockUtils.makeFakeUser();
        userEntity.setPassword("newPassword123");
        final UserDto newUserDto = new UserDto();
        newUserDto.setName(userEntity.getName());
        newUserDto.setEmail(userEntity.getName());
        newUserDto.setPassword("newPassword123");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        final UserDto addedUserDto = userService.createUser(newUserDto);
        assertAll("Equality check",
                () -> assertEquals(userEntity.getId(), addedUserDto.getId()),
                () -> assertEquals(userEntity.getName(), addedUserDto.getName()),
                () -> assertEquals(userEntity.getEmail(), addedUserDto.getEmail()),
                () -> assertEquals(userEntity.getPassword(), addedUserDto.getPassword()));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUserOk() {
        when(userRepository.findById(3L)).thenReturn(Optional.of(userEntityList.get(2)));
        final UserEntity updatedUserEntity = userEntityList.get(2);
        updatedUserEntity.setPassword("newPassword123");
        final UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(updatedUserEntity.getId());
        updatedUserDto.setName(updatedUserEntity.getName());
        updatedUserDto.setEmail(updatedUserEntity.getEmail());
        updatedUserDto.setPassword("newPassword123");
        when(userRepository.save(userEntityList.get(2))).thenReturn(userEntityList.get(2));
        userService.updateUser(updatedUserDto, 3L);
        assertAll("Equality check",
                () -> assertEquals(updatedUserEntity.getId(), userEntityList.get(2).getId()),
                () -> assertEquals(updatedUserEntity.getName(), userEntityList.get(2).getName()),
                () -> assertEquals(updatedUserEntity.getEmail(), userEntityList.get(2).getEmail()),
                () -> assertEquals(updatedUserEntity.getPassword(), userEntityList.get(2).getPassword()));
        verify(userRepository).save(userEntityList.get(2));
    }

    @Test
    void testUpdateUserNotFoundEx() {
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(MockUtils.makeUserDtoFromUserEntity(MockUtils.makeFakeUser()), 5L));
        verify(userRepository).findById(5L);
    }
}