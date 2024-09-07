package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    // TODO: Inject Rating service
    private final RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(@AuthenticationPrincipal UserDetails currentUser, Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingService.getRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(@AuthenticationPrincipal UserDetails currentUser, Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@AuthenticationPrincipal UserDetails currentUser, @Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.saveRating(rating);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        ratingService.saveRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
