package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        // Aucun initialisation supplémentaire nécessaire ici
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void home_shouldReturnUserList() throws Exception {
        User user1 = User.builder().id(1).username("user1").password("password1").roles(Set.of("ROLE_USER")).build();
        User user2 = User.builder().id(2).username("user2").password("password2").roles(Set.of("ROLE_ADMIN")).build();

        // Mocking the userService
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Simulating a GET request to "/user/list"
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void validate_shouldSaveUser() throws Exception {
        User user = User.builder().username("testUser").password("Password123!").roles(Set.of("ROLE_USER")).build();

        // Mocking the saveUser method
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Simulating a POST request to "/user/validate" with CSRF
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "testUser")
                        .param("fullname","testUser")
                        .param("password", "Password123!")
                        .param("roles", "ROLE_USER"))
                .andExpect(redirectedUrl("/user/list"))
                .andReturn();

        // Verify that saveUser was called once
        verify(userService, times(1)).saveUser(any(User.class));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void showUpdateForm_shouldReturnUserUpdateView() throws Exception {
        User user = User.builder().id(1).username("testUser").password("testPassword").roles(Set.of("ROLE_USER")).build();

        // Mocking the getUserById method
        when(userService.getUserById(1)).thenReturn(user);

        // Simulating a GET request to "/user/update/{id}"
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteUser_shouldDeleteUserAndRedirect() throws Exception {
        User user = User.builder().id(1).username("testUser").password("testPassword").roles(Set.of("ROLE_USER")).build();

        // Mocking the getUserById method
        when(userService.getUserById(1)).thenReturn(user);

        // Simulating a GET request to "/user/delete/{id}"
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).deleteUserById(1); // Vérification que deleteUserById a été appelé une fois
    }
}
