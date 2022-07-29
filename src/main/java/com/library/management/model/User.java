package com.library.management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


import lombok.Data;

@Entity
@Data
public class User {

	// Primary key
	@Id
	private String username;
	private String name;
	private int age;
	private String password;
	@Transient
	private String confirmPassword;
	private String userType;
	private String profession;
	private String address;
	private String city;
	private String state;
	private String country;
	private int mobile;

	
	
	public User(int userId, String name, int age, String username, String password, String confirmPassword, String profession, String address,
			String city, String state, String country, int mobile) {
		this.name = name;
		this.age = age;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.profession = profession;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.mobile = mobile;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
	}

}
