package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
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
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(1)
                .asOfDate(new Timestamp(System.currentTimeMillis()))
                .term(1.0)
                .value(100.0)
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showCurvePointListView() throws Exception {
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);

        when(curvePointService.getCurvePoints()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showAddCurvePointView() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testValidateAddCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("curveId", "1")
                        .param("asOfDate", "2024-10-05 12:00:00")
                        .param("term", "1.0")
                        .param("value", "100.0"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showUpdateCurvePointView() throws Exception {
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testUpdateCurvePoint() throws Exception {
        when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "1")
                        .param("asOfDate", "2024-10-05 12:00:00")
                        .param("term", "1.5") // Updated term
                        .param("value", "150.0")) // Updated value
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testDeleteCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}
