package com.example.starter;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository){
        this.repository = repository;
    }
    @GetMapping("/users")
    List<User> all(){
        return repository.findAll();
    }
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return repository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }
    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return repository.save(newUser);
    }
    @PutMapping("/users/{id}")
    User updatedUser(@RequestBody User updatedUser,@PathVariable Long id){
        return repository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return repository.save(user);
        }).orElseGet(()->{
            updatedUser.setId(id);
            return repository.save(updatedUser);
        });

    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        repository.deleteById(id);
    }
}
