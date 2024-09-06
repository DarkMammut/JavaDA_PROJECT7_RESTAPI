package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Transactional
    public List<Trade> findAllTrades() {
        return tradeRepository.findAll();
    }

    @Transactional
    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Transactional
    public Trade findTradeById(Integer id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        return optionalTrade.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    @Transactional
    public Trade updateTrade(Integer id, Trade updatedTrade) {
        Trade trade = findTradeById(id);
        trade.setAccount(updatedTrade.getAccount());
        trade.setType(updatedTrade.getType());
        trade.setBuyQuantity(updatedTrade.getBuyQuantity());
        // ... set other fields
        return tradeRepository.save(trade);
    }

    @Transactional
    public void deleteTrade(Integer id) {
        Trade trade = findTradeById(id);
        tradeRepository.delete(trade);
    }
}
