package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        // Setup mock data or behavior if needed
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testListBidLists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testValidateBidList() throws Exception {
        BidList bidList = new BidList(); // Initialize with test data
        when(bidListService.saveBidList(bidList)).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("bidList", bidList))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateBidList() throws Exception {
        BidList bidList = new BidList(); // Initialize with test data
        when(bidListService.getBidListById(1)).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/update"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateBidListPost() throws Exception {
        BidList bidList = new BidList(); // Initialize with test data
        when(bidListService.saveBidList(bidList)).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("bidList", bidList))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testDeleteBidList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"))
                .andDo(MockMvcResultHandlers.print());
    }
}
