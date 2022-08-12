package com.library.management.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;
import com.library.management.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private BookService bookService;

	private static String userType = "";

	static List<Book> relatedBooks = new ArrayList<>();
	
	private static String currentPage ="home";
	
	private static String message ="";

	@GetMapping("/user/reservations")
	public String getReservations(Model model) {
		message="";
		currentPage="/user/reservations";
		List<Reservation> reservations = reservationService.getReservationsByUsername(HomeController.username);
		model.addAttribute("username", HomeController.username);
		model.addAttribute("message", message);
		
		if (reservations.size() == 0) {
			HomeController.errorMessage = "There is no pending reservation for you.";
		}
		else {
			for (Reservation reservation : reservations) {
				Book book = bookService.getBookById(reservation.getResourceId());
				reservation.setTitle(book.getTitle());
				reservation.setAuthor(book.getAuthor());
				reservation.setIsbn(book.getIsbn());
				reservation.setCategory(book.getCategory());
				reservation.setPublisher(book.getPublisher());
				reservation.setPageCount(book.getPageCount());
				reservation.setFormattedBorrowDate(reservationService.changeDateFormat(reservation.getBorrowDate()));
			}
			HomeController.errorMessage = "";
			model.addAttribute("reservations", reservations);
		}
		model.addAttribute("errorMessage", HomeController.errorMessage);
		HomeController.errorMessage="";
		return "customer/reservations";
	}

	@GetMapping("/user/history")
	public String getAllReservationsByUsername(Model model) {
		message="";
		currentPage="/user/history";
//		List<Reservation> reservedBooks = new ArrayList<>();
		List<Reservation> reservations = userService.getUserById(HomeController.username).getReservations();
		model.addAttribute("username", HomeController.username);
		model.addAttribute("message", message);
		if (reservations.size() == 0) {
			HomeController.errorMessage = "There is no history available for you.";
		} else {
			for (Reservation reservation : reservations) {
				Book book = bookService.getBookById(reservation.getResourceId());
				reservation.setTitle(book.getTitle());
				reservation.setAuthor(book.getAuthor());
				reservation.setIsbn(book.getIsbn());
				reservation.setCategory(book.getCategory());
				reservation.setPublisher(book.getPublisher());
				reservation.setPageCount(book.getPageCount());
				reservation.setFormattedBorrowDate(reservationService.changeDateFormat(reservation.getBorrowDate()));
				if(reservation.getReturnDate() != null) {
					reservation.setFormattedReturnDate(reservationService.changeDateFormat(reservation.getReturnDate()));
				}
			}
			HomeController.errorMessage = "";
			model.addAttribute("reservations", reservations);
		}
		model.addAttribute("errorMessage", HomeController.errorMessage);
		HomeController.errorMessage="";
		return "customer/history";
	}

	@GetMapping("/user/reserve")
	public String reserveBook(Model model) {
		message="";
		model.addAttribute("username", HomeController.username);
		model.addAttribute("errorMessage", HomeController.errorMessage);
		model.addAttribute("books", relatedBooks);
		model.addAttribute("book", new Book());
		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "customer/reserveBookFormCheckBookAvailabilty";
	}

	@PostMapping("/user/reserve/getBooks")
	public String getBooks(@ModelAttribute("book") Book book, Model model) {
		relatedBooks.clear();
		message="";
		List<Book> books = bookService.findRelatedBooks(book);
		if (books.size() == 0) {
			HomeController.errorMessage = "No books found related to your search";
		} else {
			HomeController.errorMessage = "";
			relatedBooks = books;
		}

		if (HomeController.username.equals(""))
			return "redirect:/";
		else
			return "redirect:/user/reserve";
	}

//	 Get book by id (if exists)
	@GetMapping("/user/reserve/book")
	public String reserveBook(@RequestParam("bookId") String bookId) {
		message="";
		Reservation reservation = new Reservation();
		reservation.setResourceId(Integer.parseInt(bookId));
		reservation.setBorrowDate(new Date().toString());
		reservation.setWhoReserved(HomeController.username);
		reservation.setUsername(HomeController.username);
		String message = reservationService.reserveBook(reservation);
		if (message.equals("success")) {
			return "redirect:/user/reservations";
		}
		else {
			HomeController.errorMessage = message;
		}
		return "redirect:/user/reserve";
	}
//	
	@GetMapping("user/return/book")
	public String returnBook(@RequestParam("bookId") String bookId, Model model) {
		reservationService.returnBook(Integer.parseInt(bookId));
		message="Book has been returned successfully.";
		return "redirect:" + currentPage;
	}
	
	@GetMapping("/admin/users/admin")
	public String getAdminUsers(Model model) {
		List<User> adminUsers = userService.getAllAdminUsers();
		User adminUser = new User();
		model.addAttribute("user", adminUser);
		model.addAttribute("adminUsers", adminUsers);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
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
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/users/adminUsersCustomers";
	}

	@GetMapping("admin/users/DeleteUser")
	public String deleteUser(@RequestParam(value = "username", required = true) String username, Model model) {
		String type = userService.deleteUser(username);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/users/" + type;
	}

	@GetMapping("admin/users/modifyUser")
	public String modifyAdminUser(@RequestParam(value = "username", required = true) String username, Model model) {
		User user = userService.getUserById(username);
		userType = user.getUserType();
		model.addAttribute("user", user);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/users/updateUserForm";
	}

	@PostMapping("/admin/users/updateUser/{username}")
	public String updateBook(@PathVariable("username") String username, @ModelAttribute("user") User user, Model model) {
		user.setUserType(userType);
		userService.updateUserById(user);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/users/" + userType;
	}

	@GetMapping("/admin/users/addUser")
	public String addUser(@RequestParam(value = "userType", required = true) String usertype, Model model) {
		User user = new User();
		userType = usertype;
		model.addAttribute("user", user);
		model.addAttribute("errorMessage", "");
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/users/addUserForm";
	}

	@PostMapping("/admin/users/addUserConfirmation")
	public String addUserConfirmation(@ModelAttribute("user") User user, Model model) {
		user.setUserType(userType);
		userService.addUser(user);
		model.addAttribute("userType", userType);
		model.addAttribute("username", HomeController.username);
		if(HomeController.username.equals(""))
			return "redirect:/";
		else
			return "admin/users/addUserConfirmation";
	}
}
