package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.model.User;
import com.agritech_innovator.gardenguardian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showDashboard(Model model) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetch user details
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("fullName", user.getFullName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("role", user.getRole());
            model.addAttribute("memberSince", user.getCreatedAt());
        }

        // Add some dashboard statistics
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("activeUsers", userService.getActiveUsers());

        return "home";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        }

        return "profile";
    }
}