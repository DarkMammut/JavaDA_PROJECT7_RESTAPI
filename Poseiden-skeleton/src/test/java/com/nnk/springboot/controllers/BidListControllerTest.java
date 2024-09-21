package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    private BidList bidList;

    @BeforeEach
    public void setUp() {
        bidList = BidList.builder()
                .account("Test Account")
                .type("Test Type")
                .bidQuantity(100.0)
                .askQuantity(200.0)
                .bid(150.0)
                .ask(250.0)
                .benchmark("Test Benchmark")
                .bidListDate(new Timestamp(System.currentTimeMillis()))
                .commentary("Test Commentary")
                .security("Test Security")
                .status("Test Status")
                .trader("Test Trader")
                .book("Test Book")
                .creationName("Test Creator")
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showBidListView() throws Exception {
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList);

        when(bidListService.getBidLists()).thenReturn(bidLists);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showAddBidView() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testValidateAddBid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("bidQuantity", "100.0"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showUpdateBidView() throws Exception {
        when(bidListService.getBidListById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testUpdateBid() throws Exception {
        when(bidListService.getBidListById(1)).thenReturn(bidList);
        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("bidQuantity", "150.0"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testDeleteBid() throws Exception {
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(view().name("redirect:/bidList/list"));
    }
}
