package com.example.starter;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id){
        super("Could not find user with id = " + id);
    }
}
