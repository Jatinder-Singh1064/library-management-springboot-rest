package com.library.management.controller;

import com.library.management.config.FindCurrentUserService;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/employee")
public class EmployeeController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    FindCurrentUserService findCurrentUserService;

    @GetMapping
    public String employeeHomePage(Model model) {
        long currentUserId = findCurrentUserService.getCurrentUserId();
        User currentUser = userService.findUserById(currentUserId);
        model.addAttribute("currentUser", currentUser);
        return"employee/employee-home.html";
    }
}
