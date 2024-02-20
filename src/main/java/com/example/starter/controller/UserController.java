package com.example.starter.controller;

import java.util.List;

import com.example.starter.model.dto.UserDto;
import com.example.starter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/users")
    List<UserDto> getAll(){
        return userService.getAll();
    }
    @GetMapping("/users/{id}")
    UserDto findOne(@PathVariable Long id){
        return userService.findOne(id);
    }
    @PostMapping("/users")
    UserDto createUser(@RequestBody @Valid UserDto newUser){
        return userService.createUser(newUser);
    }
    @PutMapping("/users/{id}")
    UserDto updatedUser(@RequestBody @Valid UserDto updatedUser, @PathVariable Long id){
        return userService.updateUser(updatedUser, id);
    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}