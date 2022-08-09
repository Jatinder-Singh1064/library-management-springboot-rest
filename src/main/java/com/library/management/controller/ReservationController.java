package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;
import com.library.management.service.UserService;
import com.library.management.service.UserValidation;

@Controller

public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private BookService bookService;

	// Get all reservations
//	@GetMapping("/admin/reservations")
//	public List<Reservation> getAllReservations() {
//		return reservationService.getAllReservations();
//	}

	// Get all reservations
	@GetMapping("/admin/reserve/checkUser")
	public String getReserveBookForm(Model model) {
//		Reservation reservation = new Reservation();
//		model.addAttribute("isbnRequested", "");
//		errorMessage = "";
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", HomeController.errorMessage);
		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/reservations/reserveBookFormCheckUser";
	}

	@PostMapping("/admin/reserve/checkBook")
	public String checkUsername(@ModelAttribute("user") User user, Model model) {
		System.out.println("60:" + user.getUsername());
		boolean isUsernameRegistered = userValidation.doesUsernameExists(user.getUsername());
		System.out.println("62: " + isUsernameRegistered);
		model.addAttribute("username", HomeController.username);
		if (HomeController.username.equals(""))
			return "redirect:/";
		else if (isUsernameRegistered) {
			model.addAttribute(user.getUsername());
			model.addAttribute("book", new Book());
			HomeController.errorMessage = "";
			return "admin/reservations/reserveBookFormCheckBookAvailabilty";
		}

		else {
			HomeController.errorMessage = "Username is not registered !!!";
			return "redirect:/admin/reserve/checkUser";
		}
	}

	@PostMapping("/admin/reserve/getBooks")
	public String getBooks(@ModelAttribute("book") Book book, Model model) {
		System.out.println("86:" + book);
//		boolean isUsernameRegistered = userValidation.doesUsernameExists(user.getUsername());
		List<Book> relatedBooks = bookService.findRelatedBooks(book);
		if (relatedBooks.size() == 0) {
			// model.addAttribute("errorMessage", errorMessage);
			HomeController.errorMessage = "No books found related to your search";
		} else {
			model.addAttribute("books", relatedBooks);
		}
		
		if (HomeController.username.equals(""))
			return "redirect:/";
		else 
			return "redirect:/admin/reserve/checkBook";
		}
//		System.out.println("62: " + isUsernameRegistered);
//		model.addAttribute("username", HomeController.username);
//		if(HomeController.username.equals(""))
//			return "redirect:/";
//		else
//			if(isUsernameRegistered) {
//				model.addAttribute(user.getUsername());
//				model.addAttribute("book", new Book());
//				errorMessage= "";
//				return "admin/reservations/reserveBookFormCheckBookAvailabilty";
//			}
//				
//			else {
//				errorMessage = "Username is not registered !!!";
//				return "redirect:/admin/reserve/checkUser";
////			}
//		System.out.println("105: " + relatedBooks);
//		return "admin/reservations/reserveBookFormCheckBookAvailabilty";
//	}

	// Get book by id (if exists)
//	@GetMapping("/reservation/{transaction_id}")
//	public Reservation getReservation(@PathVariable int transaction_id) {
//		return reservationService.getReservationById(transaction_id);
//	}
//	
//	//	Reserve a book (if available)
//	@RequestMapping(value = "/reserve",
//			method = RequestMethod.POST,
//			consumes  = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.ALL_VALUE
//			)
//	public String reserveBook(@RequestBody Reservation reservation) {
//		return reservationService.reserveBook(reservation);
//	}
//	
//	//	Return a book (if exists)
//	@RequestMapping(value = "/return/{student_id}/{book_id}",
//			method = RequestMethod.PUT,
//			produces = MediaType.ALL_VALUE
//			)
//	public String returnBook(@PathVariable String student_id, @PathVariable String book_id) {
//		return reservationService.returnBook(student_id, book_id);
//	}

//	//	Return list of books student has borrowed (if any)
//	@GetMapping("/books/student/{student_id}")
//	public List<Book> getReservedBooksByStudent(@PathVariable String student_id) {
//		return reservationService.getReservedBooksByStudentId(student_id);
//	}
//	
//	//	Return student who has borrowed a book with book_id (if any)
//	@GetMapping("/student/book/{book_id}")
//	public Student getStudentWithBook(@PathVariable String book_id) {
//		return reservationService.getStudentWithReservedBook(book_id);
//	}

}
