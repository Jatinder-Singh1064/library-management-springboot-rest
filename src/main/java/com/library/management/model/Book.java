package com.library.management.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

//To define that this entity (class) is going to persist in database
@Entity

//For setter and getter methods
@Data
public class Book {
	
	// Primary key
	@Id
	private String resourceId;
	private String isbn;
	private String title;
	private String category;
	private String author;
	private String publisher;
	private int	pageCount;
	
	// Defining relationship between 2 entities (Book and Reservation)
//	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	
	// Join column with other table
//	@JoinColumn(name = "resourceId")
	
	// Column which is ignored in the output
//	@JsonIgnore
//	private List<Reservation> reservations;
	
}
