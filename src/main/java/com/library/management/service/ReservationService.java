package com.library.management.service;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReservationRepository;

@Service
public class ReservationService {


	@Autowired
	private ReservationRepository reservationRepository;
	
//		Method for getting all the students
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<>();
		reservations = (List<Reservation>) reservationRepository.findAll();
		return reservations;
	}
	
	
	
	//	Method for getting book by id
	public Reservation getReservationById(int id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		return reservation.orElseGet(null);
	}
	
	
// 	Method for reserving a book
	public String reserveBook(Reservation reservation) {
		String bookId = reservation.getResourceId();
		List<Reservation> reservations = new ArrayList<>();
		reservations = getAllReservations();
		boolean valid = true;
		String message;
		for(Reservation reserve: reservations) {
			if(reserve.getResourceId().equals(bookId) && reserve.getReturnDate() == null)
				valid = false;
		}
		if(valid) {
			reservationRepository.save(reservation);
			message = "Book is borrowed to student succesfully";
		}
		else {
			message = "Book is already borrowed to some other student";
		}
		return message;
	}
	
//	// 	Method for returning a book and adding back it to database
//	public String returnBook(String studentId, String bookId) {
//		List<Reservation> reservations = new ArrayList<>();
//		reservations = getAllReservations();
//		boolean valid = false;
//		String message;
//		
//		for(Reservation reserve: reservations) {
//			if(reserve.getResourceId().equals(bookId) && reserve.getStudentId().equals(studentId) && reserve.getReturnDate() == null) {
//				Date returnDate = new Date();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
//                String returnDateInString = dateFormat.format(returnDate);
//                reserve.setReturnDate(returnDateInString);
//				reservationRepository.save(reserve);
//				valid =true;
//			}
//		}
//		if(valid)
//			message = "Book- " + bookId + " is returned succesfully";
//		else
//			message = "Book- " + bookId + " can not be returned because it has not been issued to any student";
//		return message;
//	}
//
//	
//	//	Method for getting a list of books by student_id (list of book which a particular student has borrowed)
//	public List<Book> getReservedBooksByStudentId(String student_id) {
//		List<Reservation> reservations = new ArrayList<>();
//		List<Book> books = new ArrayList<>();
//		reservations = studentService.getStudent(student_id).getReservations();
//		
//		for(Reservation reservation: reservations) {
//			if(reservation.getReturnDate() == null) {
//				String bookId = reservation.getResourceId();
//				Book book = bookService.getBook(bookId);
//				books.add(book);
//			}
//		}
//		return books;
//	}
//
////	Method for getting student details by book_id for particular book
//	public Student getStudentWithReservedBook(String bookId) {
//		List<Reservation> reservations = new ArrayList<>();
//		Student student = null;
//		reservations = bookService.getBook(bookId).getReservations();
//		
//		for(Reservation reservation: reservations) {
//			if(reservation.getResourceId().equals(bookId) && reservation.getReturnDate() == null) {
//				String studentId = reservation.getStudentId();
//				student = studentService.getStudent(studentId);
//			}
//		}
//		return student;
//	}
//	


}
