package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        // Setup mock data or behavior if needed
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testListRatings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testValidateRating() throws Exception {
        Rating rating = new Rating(); // Initialize with test data
        when(ratingService.saveRating(rating)).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("rating", rating))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testShowUpdateForm() throws Exception {
        Rating rating = new Rating(); // Initialize with test data
        when(ratingService.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateRating() throws Exception {
        Rating rating = new Rating(); // Initialize with test data
        when(ratingService.saveRating(rating)).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("rating", rating))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testDeleteRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
                .andDo(MockMvcResultHandlers.print());
    }
}
