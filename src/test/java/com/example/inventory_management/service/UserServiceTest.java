package com.example.inventory_management.service;

import com.example.inventory_management.dto.UserDTO;
import com.example.inventory_management.model.User;
import com.example.inventory_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testCreateUser() {

        UserDTO userDTO = new UserDTO(
                "john",
                "password123",
                "USER"
        );

        User user = new User(
                "john",
                "password123",
                "USER"
        );

        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
                .thenReturn(user);


        User result = userService.createUser(userDTO);

        assertEquals("john", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    void testCreateAdmin() {

        UserDTO userDTO = new UserDTO(
                "admin",
                "password123",
                "ADMIN"
        );

        User user = new User(
                "admin",
                "password123",
                "ADMIN"
        );

        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class)))
                .thenReturn(user);

        User result = userService.createUser(userDTO);

        assertEquals("admin", result.getUsername());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void testCreateUserInvalidRole() {

        UserDTO userDTO = new UserDTO(
                "john",
                "password123",
                "MANAGER"
        );

        assertThrows(
                ResponseStatusException.class,
                () -> userService.createUser(userDTO)
        );
    }

    @Test
    void testGetUserByUsername() {

        User user = new User(
                "john",
                "password123",
                "USER"
        );

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("john");

        assertEquals("john", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    void testGetUserByUsernameNotFound() {

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> userService.getUserByUsername("unknown")
        );
    }
}
