package com.example.farfaraway.controller;

import com.example.farfaraway.entity.Role;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.model.request.RegisterRequest;
import com.example.farfaraway.repository.RoleRepository;
import com.example.farfaraway.repository.UserRepository;
import com.example.farfaraway.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String getRegister(Model model) {
        RegisterRequest request = new RegisterRequest();
        model.addAttribute("registrationData", request);
        return "auth/register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registrationData") @Valid RegisterRequest registrationData, BindingResult result, Model model) {
        System.out.println(registrationData);
        if (result.hasErrors()) {
            return "register";
        }
        if (!registrationData.getIsAgree()) {
            model.addAttribute("agreeError", "You must agree to the terms and conditions.");
            return "register";
        }
        try {
            Role roleUser = roleRepository.findByName("User").get();
            User user =new User();
            user.setName(registrationData.getName());
            user.setEmail(registrationData.getEmail());
            user.setPhoneNumber(registrationData.getPhone());
            user.setAddress(registrationData.getAddress());
            user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
            user.getRoles().add(roleUser);
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("saveError", "An error occurred while processing your registration.");
            return "register";
        }

    }

}
