package com.library.management.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.Reservation;
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

	public List<Reservation> getPendingReservations() {
		List<Reservation> reservations = new ArrayList<>();
		List<Reservation> allReservations = (List<Reservation>) reservationRepository.findAll();
		for (Reservation reservation : allReservations) {
			if (reservation.getReturnDate() == null) {
				reservations.add(reservation);
			}
		}
		return reservations;
	}

	// Method for getting book by id
	public Reservation getReservationById(int id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		return reservation.orElseGet(null);
	}

	// Method for reserving a book
	public String reserveBook(Reservation reservation) {
		int bookId = reservation.getResourceId();
		int booksAlreadyReserved = 0;
		List<Reservation> reservations = new ArrayList<>();
		reservations = getAllReservations();
		boolean valid = true;
		String message = "";
		for (Reservation reserve : reservations) {
			if (reserve.getUsername().equals(reservation.getUsername()) && reserve.getReturnDate() == null) {
				booksAlreadyReserved++;
			}
			if (reserve.getResourceId() == (bookId) && reserve.getReturnDate() == null)
				valid = false;
		}
		if (!valid) {
			message = "Book is already borrowed to some other student";
		}

		if (booksAlreadyReserved == 4) {
			message = "User has already reached his/her maximum limit of reserving the books";
		}
		if (valid && booksAlreadyReserved < 4) {
			reservationRepository.save(reservation);
			message = "success";
		}
		return message;
	}

	public String updateReservation(Reservation reservation) {
		int booksAlreadyReserved = 0;
		List<Reservation> reservations = new ArrayList<>();
		reservations = getAllReservations();
		String message = "";
		for (Reservation reserve : reservations) {
			if (reserve.getUsername().equals(reservation.getUsername()) && reserve.getReturnDate() == null) {
				booksAlreadyReserved++;
			}
		}
		if (booksAlreadyReserved == 4) {
			message = "User has already reached his/her maximum limit of reserving the books";
		} else {
			reservationRepository.save(reservation);
			message = "success";
		}
		return message;
	}

	public void deleteReservation(int transactionId) {
		reservationRepository.deleteById(transactionId);
	}

	public boolean isBookReserved(int id) {
		List<Reservation> reservations = getAllReservations();
		for (Reservation reservation : reservations) {
			if (reservation.getResourceId() == id && reservation.getReturnDate() == null)
				return true;
		}
		return false;
	}

	public List<Reservation> getReservationsByUsername(String username) {
		List<Reservation> reservations = new ArrayList<>();
		List<Reservation> allReservations = (List<Reservation>) reservationRepository.findAll();
		for (Reservation reservation : allReservations) {
			if (reservation.getUsername().equals(username) && (reservation.getReturnDate() == null)) {
				reservations.add(reservation);
			}
		}
		return reservations;
	}

	public void returnBook(int book_id) {
		List<Reservation> pendingReservations = getPendingReservations();
		for (Reservation reservation : pendingReservations) {
			if (reservation.getResourceId() == book_id) {
				reservation.setReturnDate(new Date().toString());
				reservationRepository.save(reservation);
			}
		}
	}

	public List<Reservation> getAllReservationsByUsername(String username) {
		List<Reservation> allReservations = new ArrayList<>();
		List<Reservation> reservations = (List<Reservation>) reservationRepository.findAll();
		;
		for (Reservation reservation : reservations) {
			if (reservation.getUsername().equals(username)) {
				allReservations.add(reservation);
			}
		}
		return allReservations;
	}

	public String changeDateFormat(String dateString) {
		String pattern = "dd-MM-yyyy";
		long longDate = Date.parse(dateString);
		Date date = new Date(longDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}

	public boolean isCustomerActive(String username) {
		List<Reservation> reservations = getAllReservations();
		for (Reservation reservation : reservations) {
			if (reservation.getUsername().equals(username) && reservation.getReturnDate() == null)
				return true;
		}
		return false;
	}
}