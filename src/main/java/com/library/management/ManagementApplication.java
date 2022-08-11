package com.library.management;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;
import com.library.management.service.UserService;
import com.library.management.model.Book;
import com.library.management.model.Reservation;

@SpringBootApplication
public class ManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Autowired 
	private UserService userService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReservationService resService;

	@Override
	public void run(String... args) throws Exception {
		User admin = new User("Jatinder Singh", 29, "jsh@gmail.com", "test", "test", "Developer", "North York, ON",
				"6842920919", "admin");
		User customer1 = new User("David Miller", 29, "dvm@gmail.com", "test", "test", "Tester", "New Jersey, US",
				"4372920902", "customer");
		User customer2 = new User("John Wick", 29, "jck@gmail.com", "test", "test", "Manager", "Missisauga, ON",
				"6372922901", "customer");
		userService.addUser(admin);
		userService.addUser(customer1);
		userService.addUser(customer2);

		Book book1 = new Book("The Pragmatic Programmer", "David Thomas, Andrew Hunt", "IT", "978-1-933624-76-1", customer1.getUserId());
		Book book2 = new Book("Clean Code", "Robert C. Martin", "IT", "978-1-900624-76-2", customer1.getUserId());
		Book book3 = new Book("Code Complete", "Steve McConnell", "IT", "478-3-900624-76-9", customer2.getUserId());
		Book book4 = new Book("Refactoring", "Martin Fowler", "IT", "878-7-900624-76-5", null);
		Book book5 = new Book("Head First Design Patterns", "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
				"IT", "578-1-300624-71-7", customer2.getUserId());
		bookService.addBook(book1);
		bookService.addBook(book2);
		bookService.addBook(book3);
		bookService.addBook(book4);
		bookService.addBook(book5);
		
		Reservation res1 = new Reservation(customer1.getUserId(), book1.getBookId(), LocalDate.of(2022, 5, 20), null, customer2.getUserId());
		Reservation res2 = new Reservation(customer1.getUserId(), book2.getBookId(), LocalDate.of(2022, 7, 25), null, null);
		
		Reservation res3 = new Reservation(customer2.getUserId(), book3.getBookId(), LocalDate.of(2022, 8, 02), null, customer1.getUserId());
		Reservation res4 = new Reservation(customer2.getUserId(), book5.getBookId(), LocalDate.of(2022, 6, 26), null, null);
		
		resService.saveReservation(res1);
		resService.saveReservation(res2);
		resService.saveReservation(res3);
		resService.saveReservation(res4);
	}
}