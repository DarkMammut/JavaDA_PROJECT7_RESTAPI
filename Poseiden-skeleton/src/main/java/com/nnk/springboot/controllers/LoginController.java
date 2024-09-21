package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller responsible for handling login-related operations.
 * This class manages user login, role-based redirections,
 * and access error handling.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    /**
     * Displays the login page.
     *
     * @return ModelAndView object with the login view.
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Redirects users to the appropriate page after login based on their role.
     * If the user has "ADMIN" authority, redirects to the admin home page;
     * otherwise, redirects to the bid list page.
     *
     * @param currentUser the currently authenticated user.
     * @return a redirection to the appropriate page depending on the user's role.
     */
    @GetMapping("default")
    public String defaultAfterLogin(@AuthenticationPrincipal UserDetails currentUser) {
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return "redirect:/admin/home";
        }
        return "redirect:/bidList/list";
    }

    /**
     * Retrieves a list of all users and displays it on the article details page.
     *
     * @return ModelAndView object with the list of users.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Displays the error page for unauthorized access attempts.
     * Also adds the current user's details to the model if they are authenticated.
     *
     * @param request the HttpServletRequest object.
     * @return ModelAndView object with the error message and user information.
     */
    @GetMapping("403")
    public ModelAndView error(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);

        Object principal = request.getUserPrincipal();
        if (principal instanceof UserDetails user) {
            mav.addObject("currentUser", user);
            mav.addObject("username", user.getUsername());
        }

        mav.setViewName("403");
        return mav;
    }

    /**
     * Processes the login form submission.
     * Checks the provided username and password against the stored credentials.
     * If authentication is successful, redirects to the default page.
     * If authentication fails, redirects to the 403 error page.
     *
     * @param username the submitted username.
     * @param password the submitted password.
     * @return ModelAndView object with a redirection depending on authentication success.
     */
    @PostMapping("login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        ModelAndView mav = new ModelAndView();
        UserDetails user = userService.loadUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            mav.setViewName("redirect:/default");
        } else {
            mav.addObject("errorMsg", "Invalid username or password");
            mav.setViewName("redirect:/403");
        }
        return mav;
    }

}
