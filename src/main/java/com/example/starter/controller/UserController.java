package com.example.starter.controller;

import java.util.List;

import com.example.starter.model.dto.UserDto;
import com.example.starter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    UserDto findOne(@PathVariable("id") Long id) {
        return userService.findOne(id);
    }

    @PostMapping("/users")
    UserDto createUser(@RequestBody @Valid UserDto newUser) {
        return userService.createUser(newUser);
    }

    @PutMapping("/users/{id}")
    UserDto updateUser(@RequestBody @Valid UserDto updatedUser, @PathVariable("id") Long id) {
        return userService.updateUser(updatedUser, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}