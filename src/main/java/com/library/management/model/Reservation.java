package com.library.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//To define that this entity (class) is going to persist in database
@Entity

//For setter and getter methods
@Data
public class Reservation {
	
	// Primary key
	@Id
	
	// Auto generated values
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	
	private String studentId;
	private String resourceId;
	
	private String borrowDate;
	private String returnDate;
	
	private double fine;
	private String whoReserved;
	
}
