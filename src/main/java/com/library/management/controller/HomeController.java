package com.library.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.library.management.model.User;
import com.library.management.service.UserValidation;

@Controller
public class HomeController {

	private String validity ="";
	
	@GetMapping("/")
	public String homepage(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		return "homepage";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginUser") User user, Model model) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("password", user.getPassword());
		return "home";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		User registerUser = new User();
		model.addAttribute("registerUser", registerUser);
		model.addAttribute("errorMessage", validity);
		return "registration";
	}

	@PostMapping("/verifyRegistration")
	public String verifyRegistration(@ModelAttribute("registeredUser") User user, Model model) {
		UserValidation uv = new UserValidation();
		validity = uv.validateUser(user);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("password", user.getPassword());
		
		if(validity.equals("success")) {
			return "confirmRegistration";
		}
		else {
			return "redirect:/register";
		}
		
	}
}
