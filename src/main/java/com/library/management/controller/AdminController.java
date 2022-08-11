package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;

@Controller
public class AdminController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/admin/books")
	public String getBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		Book book = new Book();
		model.addAttribute("book",book);
		model.addAttribute("books", books);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/books/adminBooks";
	}
	
	
	@GetMapping("/admin/users")
	public String getUsers(Model model) {
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/users/adminUsersCustomers";
//		return "admin/users/adminUsers";
	}
	

	@GetMapping("/admin/reservations")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getAllReservations();
//		Book book = new Book();
//		model.addAttribute("book",book);
		model.addAttribute("reservations", reservations);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/adminReservations";
	}
}
