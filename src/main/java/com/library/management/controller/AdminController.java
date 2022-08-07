package com.library.management.controller;

import com.library.management.config.FindCurrentUserService;
import com.library.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    @Autowired
    FindCurrentUserService findCurrentUserService;

    @GetMapping
    public String adminHome(Model model) {
        User currentUser = findCurrentUserService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "admin/admin-home.html";
    }
}
