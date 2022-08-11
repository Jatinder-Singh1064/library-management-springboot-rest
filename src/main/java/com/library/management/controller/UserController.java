package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.User;
import com.library.management.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static String userType ="";
	
	private String loginValidity = "";
	
	@GetMapping("/admin/users/admin")
	public String getAdminUsers(Model model) {
		List<User> adminUsers = userService.getAllAdminUsers();
		User adminUser = new User();
		model.addAttribute("user", adminUser);
		model.addAttribute("adminUsers", adminUsers);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/adminUsersAdmins";
	}
	
	@GetMapping("/admin/users/customer")
	public String getCustomerUsers(Model model) {
		List<User> customerUsers = userService.getAllCustomerUsers();
		User customerUser = new User();
		model.addAttribute("user", customerUser);
		model.addAttribute("customerUsers", customerUsers);
//		System.out.println(customerUsers);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/adminUsersCustomers";
	}
	
	@GetMapping("admin/users/DeleteUser")
	public String deleteAdminUser(@RequestParam(value = "userId", required = true) String userId, Model model) {
		String type = userService.deleteAdminUser(userId);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/users/" + type;
	}
	
	@GetMapping("admin/users/modifyUser")
	public String modifyAdminUser(@RequestParam(value = "userId", required = true) String userId, Model model) {
		User user = userService.getUserById(userId);
		userType = user.getUserType();
		model.addAttribute("user", user);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/updateUserForm";
	}
	
	@PostMapping("/admin/users/updateUser/{userId}")
	public String updateUser(@PathVariable("userId") String userId, @ModelAttribute("user") User user, Model model) {
		user.setUserType(userType);
		userService.updateUserById(user);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/users/" + userType;
	}

	@GetMapping("/admin/users/addUser")
	public String addUser(@RequestParam(value = "userType", required = true) String userType, Model model) {
//		book.setResourceId(Integer.parseInt(resourceId));
//		bookService.updateBookById(book);
		User user = new User();
//		userType = userType;
		System.out.println(userType);
		model.addAttribute("user", user);
		model.addAttribute("errorMessage", "");
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/addUserForm";
	}
	
	@PostMapping("/admin/users/addUserConfirmation")
	public String addUserConfirmation(@ModelAttribute("user") User user, Model model) {
		user.setUserType(userType);
		userService.addUser(user);
//		System.out.println("USERTYPPE: " +userType);
		model.addAttribute("userType", userType);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/addUserConfirmation";
	}
	

	@GetMapping("/user/reserve")
	public String reserveBook(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("errorMessage", loginValidity);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "homepage";
	}
	
	@GetMapping("/user/return")
	public String returnBook(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("errorMessage", loginValidity);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "homepage";
	}

}
