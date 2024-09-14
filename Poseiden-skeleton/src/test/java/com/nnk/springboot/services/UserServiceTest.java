package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonExistentUser"));
    }

    @Test
    void saveUser_shouldSaveUser() {
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals("testUser", savedUser.getUsername());
        assertEquals("testPassword", savedUser.getPassword());
        assertEquals(Set.of("ROLE_USER"), savedUser.getRoles());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = User.builder()
                .id(1)
                .username("testUser")
                .password("testPassword")
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1);

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        assertEquals("testPassword", foundUser.getPassword());
        assertEquals(Set.of("ROLE_USER"), foundUser.getRoles());
    }

    @Test
    void getUserById_shouldReturnNullForInvalidId() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(999);

        assertNull(foundUser);
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
        User user1 = User.builder()
                .id(1)
                .username("user1")
                .password("password1")
                .roles(Set.of("ROLE_USER"))
                .build();
        User user2 = User.builder()
                .id(2)
                .username("user2")
                .password("password2")
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        Iterable<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, ((Collection<?>) users).size());
    }

    @Test
    void deleteUserById_shouldDeleteUser() {
        User user = User.builder()
                .id(1)
                .username("testUser")
                .password("testPassword")
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUserById(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
