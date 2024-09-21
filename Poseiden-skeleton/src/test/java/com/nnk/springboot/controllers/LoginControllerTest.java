package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @BeforeEach
    public void setUp() {
        // Set up a test user
        user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode("Password123!")); // Store the encoded password
        user.setFullname("Test User");

        // Using a Set for roles
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);
        userRepository.save(user); // Save to the database for testing
    }

    @AfterEach
    public void tearDown() {
        deleteUserTest();
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void testSuccessfulLoginRedirect() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "testUser")
                        .param("password", "Password123!")
                        .with(csrf()))
                .andExpect(redirectedUrl("/default"));
    }

    @Test
    public void testFailedLoginRedirect() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "testUser")
                        .param("password", "wrongPassword")
                        .with(csrf()))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testDefaultRedirectForAdmin() throws Exception {
        // Setting up an admin user
        User adminUser = new User();
        adminUser.setUsername("adminUser");
        adminUser.setPassword(passwordEncoder.encode("adminPassword123!"));
        adminUser.setFullname("Admin User");

        // Using a Set for roles
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        adminUser.setRoles(roles);

        userRepository.save(adminUser);

        mockMvc.perform(post("/login")
                        .param("username", "adminUser")
                        .param("password", "adminPassword123!")
                        .with(csrf()))
                .andExpect(redirectedUrl("/default"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testGetUserArticles() throws Exception {
        mockMvc.perform(get("/secure/article-details"))
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    private void deleteUserTest() {
        User testUser = userRepository.findByUsername("testUser");
        User testAdmin = userRepository.findByUsername("adminUser");

        if (testUser != null) {
            userRepository.delete(testUser);
        }
        if (testAdmin != null) {
            userRepository.delete(testAdmin);
        }
    }
}
