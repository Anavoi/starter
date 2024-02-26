package com.example.starter.controller;

import com.example.starter.model.dto.UserDto;
import com.example.starter.service.UserService;
import com.example.starter.util.MockUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        final List<UserDto> userDtoList = new ArrayList<>(List.of(
                MockUtils.makeFakeUserDto(),
                MockUtils.makeFakeUserDto()));
        when(userService.getAll()).thenReturn(userDtoList);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(userDtoList.get(0).getId()))
                .andExpect(jsonPath("$.[0].name").value(userDtoList.get(0).getName()))
                .andExpect(jsonPath("$.[0].email").value(userDtoList.get(0).getEmail()))
                .andExpect(jsonPath("$.[0].password").value(userDtoList.get(0).getPassword()))
                .andExpect(jsonPath("$.[1].id").value(userDtoList.get(1).getId()))
                .andExpect(jsonPath("$.[1].name").value(userDtoList.get(1).getName()))
                .andExpect(jsonPath("$.[1].email").value(userDtoList.get(1).getEmail()))
                .andExpect(jsonPath("$.[1].password").value(userDtoList.get(1).getPassword()));
        verify(userService).getAll();
    }

    @Test
    void shouldReturnUser() throws Exception {
        final UserDto existingUserDto = MockUtils.makeFakeUserDto();
        when(userService.findOne(any())).thenReturn(existingUserDto);
        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingUserDto.getId()))
                .andExpect(jsonPath("$.name").value(existingUserDto.getName()))
                .andExpect(jsonPath("$.email").value(existingUserDto.getEmail()))
                .andExpect(jsonPath("$.password").value(existingUserDto.getPassword()));
        verify(userService).findOne(any());
    }

    @Test
    void shouldReturnNewUser() throws Exception {
        final UserDto createdUserDto = MockUtils.makeFakeUserDto();
        when(userService.createUser(any())).thenReturn(createdUserDto);
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createdUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUserDto.getId()))
                .andExpect(jsonPath("$.name").value(createdUserDto.getName()))
                .andExpect(jsonPath("$.email").value(createdUserDto.getEmail()))
                .andExpect(jsonPath("$.password").value(createdUserDto.getPassword()));
        verify(userService).createUser(any());
    }

    @Test
    void shouldReturnUpdatedUser() throws Exception {
        final UserDto updatedUserDto = MockUtils.makeFakeUserDto();
        when(userService.updateUser(any(), any())).thenReturn(updatedUserDto);
        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUserDto.getId()))
                .andExpect(jsonPath("$.name").value(updatedUserDto.getName()))
                .andExpect(jsonPath("$.email").value(updatedUserDto.getEmail()))
                .andExpect(jsonPath("$.password").value(updatedUserDto.getPassword()));
        verify(userService).updateUser(any(), any());
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
        final UserDto notValidEmailUserDto = MockUtils.makeFakeUserDtoNotValidEmail();
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(notValidEmailUserDto)))
                .andExpect(status().isBadRequest());
    }
}
