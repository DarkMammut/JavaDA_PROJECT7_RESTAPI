package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        List<Trade> trades = tradeService.findAllTrades();
        model.addAttribute("trades", trades);
        model.addAttribute("currentUser", currentUser);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(@AuthenticationPrincipal UserDetails currentUser, Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@AuthenticationPrincipal UserDetails currentUser, @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.saveTrade(trade);
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
