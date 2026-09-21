package com.example.inventory_management.controller;

import com.example.inventory_management.dto.UserResponseDTO;
import com.example.inventory_management.model.User;
import com.example.inventory_management.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.inventory_management.dto.UserDTO;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
    @GetMapping("/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username) {

        User user = userService.getUserByUsername(username);
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
