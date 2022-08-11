package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.service.BookService;
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
	private BookService bookService;
	
	@Autowired
	private LoginValidation loginValidation;
	
	private String validity ="";
	
	private String loginValidity = "";
	
	static String userId = "";
	
	static String errorMessage = "";
	
	@GetMapping("/")
	public String homepage(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("errorMessage", loginValidity);
		return "home/homepage";
	}
	
	
	@GetMapping("/register")
	public String register(Model model) {
		User registerUser = new User();
		model.addAttribute("registerUser", registerUser);
		model.addAttribute("errorMessage", validity);
		return "home/registration";
	}

	@PostMapping("/verifyRegistration")
	public String verifyRegistration(@ModelAttribute("registeredUser") User user, Model model) {
		validity = userValidation.validateUser(user);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("password", user.getPassword());
		
		if(validity.equals("success")) {
			validity ="";
			user.setUserType("customer");
			userService.addUser(user);
			return "home/confirmRegistration";
		}
		else {
			return "redirect:/register";
		}
	}
	
	@PostMapping("/home")
	public String redirectToHomepage(@ModelAttribute("loginUser") User user, Model model) {
		loginValidity = loginValidation.validateUser(user);
		userId = user.getUserId();
		model.addAttribute("userId", userId);
		if(loginValidity.equals("success")) {
			loginValidity = "";
			String userType = loginValidation.getUserType(user);
//			System.out.println(userType);
			if(userType.equals("customer")) {
				List<Reservation> bookReservationsByUser = bookService.getBookReservationsByUserId(userId);
				List<Book> userBooks = bookService.getAllBooksByUser(userId);
				model.addAttribute("userReservations", bookReservationsByUser);
				model.addAttribute("userBooks", userBooks);
				return "home/homepageUser";
			}				
			else
				return "home/homepageAdmin";
		}
		else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		userId="";
		errorMessage ="";
		return "redirect:/";
	}
}
