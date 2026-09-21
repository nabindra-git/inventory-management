package com.example.inventory_management.service;

import com.example.inventory_management.dto.UserDTO;
import com.example.inventory_management.model.User;
import com.example.inventory_management.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {

        if (!userDTO.getRole().equals("USER") &&
                !userDTO.getRole().equals("ADMIN")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Role must be USER or ADMIN"
            );
        }

        User user = new User(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole()
        );

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }
}