package com.library.management.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

//To define that this entity (class) is going to persist in database
@Entity

//For setter and getter methods
@Data
@NoArgsConstructor
public class Book {
	
	// Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	private String isbn;
	private String title;
	private String category;
	private String author;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "bookId")
	@JsonIgnore
	private List<Reservation> reservations;
	
	/* @ManyToOne */
	private String userId;
	
	public Book(String title, String author, String category, String isbn, String userId) {
		super();
		this.title = title;
		this.author = author;
		this.category = category;
		this.isbn = isbn;
		this.userId = userId;
	}
	
}
