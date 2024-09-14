package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        // Setup mock data or behavior if needed
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testListTrades() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testValidateTrade() throws Exception {
        Trade trade = new Trade(); // Initialize with test data
        when(tradeService.saveTrade(trade)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("trade", trade))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testShowUpdateForm() throws Exception {
        Trade trade = new Trade(); // Initialize with test data
        when(tradeService.findTradeById(1)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/update"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testUpdateTrade() throws Exception {
        Trade trade = new Trade(); // Initialize with test data
        when(tradeService.updateTrade(1, trade)).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("parameterName", "value") // replace with actual parameters
                        .flashAttr("trade", trade))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    @Test
    public void testDeleteTrade() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
                .andDo(MockMvcResultHandlers.print());
    }
}
