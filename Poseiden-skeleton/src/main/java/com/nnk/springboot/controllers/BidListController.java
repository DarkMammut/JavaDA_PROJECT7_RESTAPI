package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

@Controller
@RequiredArgsConstructor
public class BidListController {
    private final BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(@AuthenticationPrincipal UserDetails currentUser, Model model)
    {
        System.out.println(currentUser);
        model.addAttribute("bidLists", bidListService.getBidLists());
        model.addAttribute("currentUser", currentUser);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(@AuthenticationPrincipal UserDetails currentUser, BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@AuthenticationPrincipal UserDetails currentUser, @Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.saveBidList(bid);
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidListById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        bidListService.saveBidList(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
