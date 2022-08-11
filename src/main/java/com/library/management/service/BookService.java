package com.library.management.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReservationRepository;
import com.library.management.repository.UserRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private UserRepository userRepository;

//		Method for getting all the books
	public List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

//	Method for getting all the User Book Reservations
	public List<Reservation> getBookReservationsByUserId(String userId) {
		List<Reservation> allReservations = (List<Reservation>) reservationRepository.findAll();
		List<Reservation> userReservations = new ArrayList<Reservation>();
		
		for(Reservation res : allReservations) {
			if(res.getReservedByUserId() != null) 
				if(res.getReservedByUserId().equals(userId)) 
					userReservations.add(res);
		}
		return userReservations;
	}

	public List<Book> getAllBooksByUser(String userId) {
		List<Book> books = (List<Book>) bookRepository.findAll();
		List<Book> userBooks = new ArrayList<Book>();
		
		for(Book book : books) {
			if(book.getUserId() != null) 
				if(book.getUserId().equals(userId)) 
					userBooks.add(book);
		}
		return userBooks;
	}

	// Method for getting book by id
	public Book getBookById(int id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.orElseGet(null);
	}

	// Method for adding a student into database
	public void addBook(Book book) {
		bookRepository.save(book);
	}

// Method for updating student into database (only if it exists)
//	public Student updateStudent(String id, Student student) {
//		Optional<Student> student1  = studentRepository.findById(id);
//		if(student1 != null) {
//			studentRepository.save(student);
//		}
//		Optional<Student> student2 = studentRepository.findById(id);
//		return student2.orElseGet(null);
//	}
//	
	// Deleting a book by id
	public void deleteBook(int id) {
		bookRepository.deleteById(id);
	}

	public void updateBookById(Book updatedBook) {
		bookRepository.save(updatedBook);
	}

	public List<Book> findRelatedBooks(Book book) {
		// TODO Auto-generated method stub
		HashSet<Book> relatedBooks = new HashSet<>();
		List<Book> books = getAllBooks();
		for (Book b : books) {
			if (b.getIsbn().equals(book.getIsbn())) {
				relatedBooks.add(b);
			}
			if (b.getAuthor().contains(book.getAuthor()) && !book.getAuthor().equals("")) {
				relatedBooks.add(b);
			}
			if (b.getCategory().contains(book.getCategory()) && !book.getCategory().equals("")) {
				relatedBooks.add(b);
			}
			if (b.getTitle().contains(book.getTitle()) && !book.getTitle().equals("")) {
				relatedBooks.add(b);
			}
		}
		List<Book> relatedAllBooks = (ArrayList<Book>) relatedBooks.stream().collect(Collectors.toList());
		return relatedAllBooks;
	}

// 	Method for returning a book by user
	public String returnBookByUser(int bookId, String userId) {
		Book book = bookRepository.findById(bookId).get();
				
		for(Reservation reservation : book.getReservations()) {
			if(reservation.getUserId().equals(userId) && reservation.getReturnDate() != null) {
				reservation.setBorrowDate(null);
				reservation.setReturnDate(LocalDate.now());
				reservation.setUserId(null);
				reservationRepository.save(reservation);
			}
		}
		book.setUserId(null);
		bookRepository.save(book);
		return "Book is returned succesfully";
	}

}
