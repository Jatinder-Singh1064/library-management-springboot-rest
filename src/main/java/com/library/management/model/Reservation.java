package com.library.management.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

//To define that this entity (class) is going to persist in database
@Entity
//For setter and getter methods
@Data
public class Reservation {
	//Primary key
	@Id	
	//Auto generated values
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reservationId;
	private String userId;
	private int bookId;
	private LocalDate borrowDate = null;
	private LocalDate returnDate = null;
	private String reservedByUserId;
	
//	@Transient
//	private String isbnRequested;
//	
//	public Reservation(String username, String isbnRequested){
//		this.username = username;
//		this.isbnRequested = isbnRequested;
//	}
	
	public Reservation() {
		
	}
	
	public Reservation(String userId, int bookId, LocalDate borrowDate, LocalDate returnDate, String reservedByUserId) {
		this.userId = userId;
		this.bookId = bookId;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.reservedByUserId = reservedByUserId;
	}
}
