package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = Rating.builder()
                .id(1)
                .moodysRating("AAA")
                .sandPRating("AA")
                .fitchRating("A+")
                .orderNumber(1)
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showRatingListView() throws Exception {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        when(ratingService.getRatings()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showAddRatingView() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testValidateAddRating() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("moodysRating", "AAA")
                        .param("sandPRating", "AA")
                        .param("fitchRating", "A+")
                        .param("orderNumber", "1"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showUpdateRatingView() throws Exception {
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testUpdateRating() throws Exception {
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(post("/rating/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodysRating", "A+")
                        .param("sandPRating", "A")
                        .param("fitchRating", "B")
                        .param("orderNumber", "2"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testDeleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(view().name("redirect:/rating/list"));
    }
}
