package com.library.management.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;
import com.library.management.service.UserValidation;

@Controller

public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private BookService bookService;

	private static String usernameRequested = "";
	private static List<Book> relatedBooks = new ArrayList<>();

	@GetMapping("/admin/allTransactions")
	public String getAllReservations(Model model) {
		List<Reservation> reservations = reservationService.getAllReservations();
		model.addAttribute("reservations", reservations);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/allTransactions";
	}
	
	// Get all reservations
	@GetMapping("/admin/reserve/checkUser")
	public String getReserveBookForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", HomeController.errorMessage);
		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/reserveBookFormCheckUser";
	}

	@PostMapping("/admin/reserve/checkUser")
	public String checkUsername(@ModelAttribute("user") User user, Model model) {
		boolean isUsernameRegistered = userValidation.doesUsernameExists(user.getUsername());
		if (isUsernameRegistered) {
			HomeController.errorMessage = "";
			usernameRequested = user.getUsername();
			relatedBooks.clear();
			return "redirect:/admin/reserve/checkBook";
		}

		else {
			HomeController.errorMessage = "Username is not registered !!!";
			return "redirect:/admin/reserve/checkUser";
		}

	}

	@GetMapping("/admin/reserve/checkBook")
	public String checkBookForm(Model model) {
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", HomeController.errorMessage);
		model.addAttribute("books", relatedBooks);
		model.addAttribute("book", new Book());
		if (HomeController.username.equals(""))
			return "redirect:/";
		else {
			HomeController.errorMessage = "";
			return "admin/reservations/reserveBookFormCheckBookAvailabilty";
		}
	}

	@PostMapping("/admin/reserve/getBooks")
	public String getBooks(@ModelAttribute("book") Book book, Model model) {
		relatedBooks.clear();
		List<Book> books = bookService.findRelatedBooks(book);
		if (books.size() == 0) {
			HomeController.errorMessage = "No books found related to your search";
		} else {
			relatedBooks = books;
		}

		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/reserve/checkBook";
	}

//	 Get book by id (if exists)
	@GetMapping("/admin/reserve/book")
	public String reserveBook(@RequestParam("bookId") String bookId) {
		Reservation reservation = new Reservation();
		reservation.setUsername(usernameRequested);
		reservation.setResourceId(Integer.parseInt(bookId));
		reservation.setBorrowDate(new Date().toString());
		reservation.setWhoReserved(HomeController.username);
		String message = reservationService.reserveBook(reservation);
		if (message.equals("success")) {
			return "redirect:/admin/reservations";
		} else {
			HomeController.errorMessage = message;
			if (message.contains("maximum limit"))
				return "redirect:/admin/reserve/checkUser";
			else if (message.contains("already borrowed"))
				return "redirect:/admin/reserve/checkBook";
		}
		return "redirect:/admin/reservations/adminReservations";
	}

//	 Get book by id (if exists)
	@GetMapping("/admin/reservations/modifyReservation")
	public String modifyReservation(@RequestParam String transactionId, Model model) {
		Reservation reservation = reservationService.getReservationById(Integer.parseInt(transactionId));
		model.addAttribute("username", HomeController.username);
		model.addAttribute("reservation", reservation);
		return "admin/reservations/updateReservationForm";
	}

	@PostMapping("/admin/reservations/updateReservation")
	public String updateReservation(@ModelAttribute("reservation") Reservation reservation, String bookId) {
		String message = reservationService.updateReservation(reservation);
		if (message.equals("success")) {
			return "redirect:/admin/reservations";
		} else {
			HomeController.errorMessage = message;
			if (message.contains("maximum limit"))
				return "redirect:/admin/reserve/checkUser";
		}
		return "redirect:/admin/reservations/adminReservations";
	}

	// Get book by id (if exists)
	@GetMapping("/admin/reservations/deleteReservation")
	public String deleteReservation(@RequestParam String transactionId, Model model) {
		reservationService.deleteReservation(Integer.parseInt(transactionId));
		return "redirect:/admin/reservations";
	}

	@GetMapping("/admin/return/checkUser")
	public String getReturnBookForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", HomeController.errorMessage);
		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/returnBookFormCheckUser";
	}

	@PostMapping("/admin/return/checkUser")
	public String verifyUsername(@ModelAttribute("user") User user, Model model) {
		boolean isUsernameRegistered = userValidation.doesUsernameExists(user.getUsername());
		if (isUsernameRegistered) {
			usernameRequested =  user.getUsername();
			return "redirect:/admin/return/reservedBooks";

		} else {
			HomeController.errorMessage = "Username is not registered !!!";
			return "redirect:/admin/return/checkUser";
		}
	}

	@GetMapping("admin/return/reservedBooks")
	public String getReservedBooks(Model model) {
		List<Book> reservedBooks = new ArrayList<>();
		List<Reservation> reservations = reservationService.getReservationsByUsername(usernameRequested);

		if (reservations.size() == 0) {
			HomeController.errorMessage = "There is no pending book left for the user: " + usernameRequested;
			return "redirect:/admin/return/checkUser";
		} else {
			for (Reservation reservation : reservations) {
				Book book = bookService.getBookById(reservation.getResourceId());
				reservedBooks.add(book);
			}
			model.addAttribute("books", reservedBooks);
			model.addAttribute("username", HomeController.username);
			return "admin/reservations/reservedBooksByUsername";
		}
	}

//	 Return student who has borrowed a book with book_id (if any)
	@GetMapping("admin/return/book")
	public String returnBook(@RequestParam("bookId") String bookId) {
		reservationService.returnBook(Integer.parseInt(bookId));
		return "redirect:/admin/return/reservedBooks";
	}

}
