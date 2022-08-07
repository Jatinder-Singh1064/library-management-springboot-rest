package com.library.management.controller;

import com.library.management.model.User;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder pwEncoder;

    @GetMapping(value="/login")
    public String login() {
        return "main/login.html";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "main/logout.html";
    }

    @GetMapping(value="/register")
    public String register(Model model) {
        model.addAttribute("newUser", new User());
        return "main/register.html";
    }

    @PostMapping(value="/register/save")
    public String saveNewUser(User newUser) {
        newUser.setPassword(pwEncoder.encode(newUser.getPassword()));
        userService.saveUser(newUser);
        return "redirect:/register/userregistered";
    }

    @GetMapping(value="/register/userregistered")
    public String userRegistered() {
        return "main/user-registered.html";
    }
}
