package com.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.library.management.model.User;
import com.library.management.service.LoginValidation;
import com.library.management.service.UserService;
import com.library.management.service.UserValidation;

@Controller
public class HomeController {

	@Autowired
	private UserValidation userValidation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginValidation loginValidation;
	
	private String validity ="";
	
	@GetMapping("/")
	public String homepage(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("errorMessage", validity);
		return "homepage";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginUser") User user, Model model) {
		validity = loginValidation.validateUser(user);
		
		model.addAttribute("username", user.getUsername());
		model.addAttribute("password", user.getPassword());
		
		if(validity.equals("success")) {
			validity = "";
			return "home";
		}
		else {
			return "redirect:/";
		}
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
		validity = userValidation.validateUser(user);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("password", user.getPassword());
		
		if(validity.equals("success")) {
			validity ="";
			user.setUserType("customer");
			userService.addUser(user);
			return "confirmRegistration";
		}
		else {
			return "redirect:/register";
		}
	}
}
