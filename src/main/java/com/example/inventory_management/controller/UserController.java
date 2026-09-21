package com.example.inventory_management.controller;

import com.example.inventory_management.model.User;
import com.example.inventory_management.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid@RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping("/{username}")
    public User getUserByUsername(@Valid@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
