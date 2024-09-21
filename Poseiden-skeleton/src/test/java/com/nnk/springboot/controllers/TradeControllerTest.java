package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(100.0)
                .sellQuantity(150.0)
                .buyPrice(200.0)
                .sellPrice(250.0)
                .benchmark("Test Benchmark")
                .tradeDate(new Timestamp(System.currentTimeMillis()))
                .security("Test Security")
                .status("Test Status")
                .trader("Test Trader")
                .book("Test Book")
                .creationName("Test Creator")
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .revisionName("Test Revision")
                .revisionDate(new Timestamp(System.currentTimeMillis()))
                .dealName("Test Deal")
                .dealType("Test Deal Type")
                .sourceListId("Test Source ID")
                .side("Buy")
                .build();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showTradeListView() throws Exception {
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);

        when(tradeService.findAllTrades()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showAddTradeView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testValidateAddTrade() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("buyQuantity", "100.0")
                        .param("sellQuantity", "150.0"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void showUpdateTradeView() throws Exception {
        when(tradeService.findTradeById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testUpdateTrade() throws Exception {
        when(tradeService.findTradeById(1)).thenReturn(trade);

        mockMvc.perform(post("/trade/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("buyQuantity", "120.0")
                        .param("sellQuantity", "180.0"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testDeleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(view().name("redirect:/trade/list"));
    }
}
