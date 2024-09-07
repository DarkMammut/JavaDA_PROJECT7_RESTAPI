package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTrades_shouldReturnTrades() {
        Trade trade = Trade.builder().tradeId(1).account("TestAccount").build();
        when(tradeRepository.findAll()).thenReturn(Collections.singletonList(trade));

        List<Trade> result = tradeService.findAllTrades();

        assertEquals(1, result.size());
        assertEquals(trade, result.get(0));
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void saveTrade_shouldReturnSavedTrade() {
        Trade trade = Trade.builder().tradeId(1).account("TestAccount").build();
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade result = tradeService.saveTrade(trade);

        assertEquals(trade, result);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void findTradeById_shouldReturnTrade() {
        Trade trade = Trade.builder().tradeId(1).account("TestAccount").build();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findTradeById(1);

        assertEquals(trade, result);
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void findTradeById_shouldThrowExceptionWhenNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tradeService.findTradeById(1));
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void updateTrade_shouldReturnUpdatedTrade() {
        Trade existingTrade = Trade.builder().tradeId(1).account("OldAccount").build();
        Trade updatedTrade = Trade.builder().account("NewAccount").build();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(existingTrade);

        Trade result = tradeService.updateTrade(1, updatedTrade);

        assertEquals("NewAccount", result.getAccount());
        verify(tradeRepository, times(1)).findById(1);
        verify(tradeRepository, times(1)).save(existingTrade);
    }

    @Test
    void deleteTrade_shouldCallDelete() {
        Trade trade = Trade.builder().tradeId(1).build();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).delete(trade);

        tradeService.deleteTrade(1);

        verify(tradeRepository, times(1)).findById(1);
        verify(tradeRepository, times(1)).delete(trade);
    }
}
