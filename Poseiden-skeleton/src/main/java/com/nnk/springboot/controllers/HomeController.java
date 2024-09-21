package com.nnk.springboot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        System.out.println("USERDETAILS" +currentUser);
        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/user/list";
    }
}
