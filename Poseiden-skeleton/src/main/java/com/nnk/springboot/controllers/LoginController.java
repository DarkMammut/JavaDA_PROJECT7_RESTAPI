package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("403")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    @PostMapping("login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        ModelAndView mav = new ModelAndView();
        UserDetails user = userService.loadUserByUsername(username);
//        String encodedPassword = passwordEncoder.encode(password);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Authentication successful
            System.out.println("authenticated successfully" +user);
            mav.setViewName("redirect:/bidList/list");
        } else {
            // Authentication failed
            System.out.println("authentication failed" +user);
            mav.addObject("errorMsg", "Invalid username or password");
            mav.setViewName("redirect:/403");
        }
        return mav;
    }

}
