package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    public void testAdminHomePage() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(view().name("redirect:/user/list"));
    }
}
