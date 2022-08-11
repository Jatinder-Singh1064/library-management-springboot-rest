package com.library.management.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class User {
	// Primary key
	@Id
	private String userId;
	private String fullName;
	private int age;
	private String password;
	@Transient
	private String confirmPassword;
	private String userType;
	private String profession;
	private String address;
	private String mobile;
	
	// Defining relationship between 2 entities (User and Reservation)
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "userId")
	@JsonIgnore
	private List<Reservation> reservations;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "bookId")
	@JsonIgnore
	private List<Book> books;

	public User(String fullName, int age, String userId, String password, String confirmPassword, String profession,
			String address, String mobile, String userType) {
		this.fullName = fullName;
		this.age = age;
		this.userId = userId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.profession = profession;
		this.address = address;
		this.mobile = mobile;
		this.userType = userType;
	}

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public User() {
	}

}
