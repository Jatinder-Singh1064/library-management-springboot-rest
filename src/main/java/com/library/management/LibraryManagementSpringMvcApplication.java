package com.library.management;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class LibraryManagementSpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSpringMvcApplication.class, args);
	}

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder pwEncoder;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			User user1 = new User("admin", pwEncoder.encode("test"), "jatinder.1064@gmail.com", "Jatinder", "Singh", "25 Parkway Forest Dr.", "01-11433823", "North York");
			user1.setRole("ROLE_ADMIN");

			User user2 = new User("employee", pwEncoder.encode("test"), "N01468035@humbermail.com", "Hemant", "Kansal", "45 Forest Manor Dr.", "01-87054875", "Brampton");
			user2.setRole("ROLE_EMPLOYEE");

			User user3 = new User("user", pwEncoder.encode("test"), "mike.robert@gmail.com", "Mike", "Robert", "10 Humber Dr.", "01-18756892", "Ajax");
			user3.setRole("ROLE_USER");

			userService.saveUser(user1);
			userService.saveUser(user2);
			userService.saveUser(user3);

			Book book1 = new Book("Don Quixote", "Franz Kafka", 1996, 1);
			Book book2 = new Book("In Search of Lost Time", "Alexandre Dumas", 2010, 2);
			Book book3 = new Book("One Hundred Years of Solitude", "Barbara Cartland", 2002, 3);
			Book book4 = new Book("The Lord of The Rings", "G.R.R. Martin", 1992, 4);
			Book book5 = new Book("The Flowers of Evil", "Paulo Coelo", 1995, 3);

			bookService.saveBook(book1);
			bookService.saveBook(book2);
			bookService.saveBook(book3);
			bookService.saveBook(book4);
			bookService.saveBook(book5);

			book2.setTheUser(user3);
			book2.setReturnDate(LocalDate.of(2022, 7, 23));

			book1.setTheUser(user3);
			book1.setReturnDate(LocalDate.of(2022, 7, 05));

			user3.setBooks(Arrays.asList(book2, book1));

			bookService.saveBook(book1);
			bookService.saveBook(book2);
			userService.saveUser(user3);
		};
	}
}
