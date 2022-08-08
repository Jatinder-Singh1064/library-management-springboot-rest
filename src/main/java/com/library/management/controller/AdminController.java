package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.LoginValidation;
import com.library.management.service.UserService;
import com.library.management.service.UserValidation;

@Controller

public class AdminController {
	
	@Autowired
	private BookService bookService;
	
	private String loginValidity = "";
	

	@GetMapping("/admin/books")
	public String getBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		Book book = new Book();
		model.addAttribute("book",book);
		model.addAttribute("books", books);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/books/adminBooks";
	}
	
	@GetMapping("/admin/users")
	public String getUsers(Model model) {
		model.addAttribute("username", HomeController.username);
		return "admin/users/adminUsers";
	}
	

	@GetMapping("/admin/reservations")
	public String reserveBook(Model model) {
		User loginUser = new User();
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", loginValidity);
		return "homepage";
	}
}
