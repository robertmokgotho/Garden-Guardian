package com.agritech_innovator.gardenguardian.controller;

import com.agritech_innovator.gardenguardian.dto.UserRegistrationDto;
import com.agritech_innovator.gardenguardian.model.User;
import com.agritech_innovator.gardenguardian.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

//    @GetMapping("/")
//    public String home() {
//        return "redirect:/login";
//    }

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }

        if (logout != null) {
            model.addAttribute("success", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }

        // Check if passwords match
        if (!registrationDto.isPasswordMatching()) {
            result.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
            return "register";
        }

        try {
            // Check if username already exists
            if (userService.existsByUsername(registrationDto.getUsername())) {
                result.rejectValue("username", "username.exists", "Username already exists");
                return "register";
            }

            // Check if email already exists
            if (userService.existsByEmail(registrationDto.getEmail())) {
                result.rejectValue("email", "email.exists", "Email already exists");
                return "register";
            }

            // Register the user
            User registeredUser = userService.registerNewUser(registrationDto);

            redirectAttributes.addFlashAttribute("success",
                    "Registration successful! You can now login with your credentials.");

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login?logout=true";
    }
}
