package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;

@Controller

public class AdminController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReservationService reservationService;
	
	static String message= "";

	@GetMapping("/admin/books")
	public String getBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books", books);
		model.addAttribute("message", message);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/books/adminBooks";
	}
	
	@GetMapping("/admin/users")
	public String getUsers(Model model) {
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/users/adminUsers";
	}
	

	@GetMapping("/admin/reservations")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getPendingReservations();
		model.addAttribute("reservations", reservations);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/adminReservations";
	}
}
