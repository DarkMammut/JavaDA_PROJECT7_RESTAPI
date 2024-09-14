package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        // Setup mock data or behavior if needed
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testListCurvePoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testValidateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(); // Initialize with test data
        when(curvePointService.saveCurvePoint(curvePoint)).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(); // Initialize with test data
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testDeleteCurvePoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"))
                .andDo(MockMvcResultHandlers.print());
    }
}
