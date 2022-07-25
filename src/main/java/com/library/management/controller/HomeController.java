package com.library.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.library.management.model.Login;
import com.library.management.model.User;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String homepage(Model model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "homepage";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("login") Login login, Model model) {
		model.addAttribute("username", login.getUsername());
		model.addAttribute("password", login.getPassword());
		return "home";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

}
