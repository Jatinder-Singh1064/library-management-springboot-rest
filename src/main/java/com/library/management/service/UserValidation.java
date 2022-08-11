package com.library.management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;

@Service
public class UserValidation {

	@Autowired
	private UserRepository userRepository;
	
	public String validateUser(User user) {
		String validity = "";
		
		String fullName = user.getFullName();
		String userId = user.getUserId();
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		String address = user.getAddress();
		
		if(fullName.equals("") || fullName == null) {
			validity = "FullName is mandatory field.";
		}
		else if(userId.equals("") || userId == null) {
			validity = "UserId is mandatory field.";
		}
		else if(doesUserIdExists(userId)) {
			validity = "UserId already exists. Please enter unique userId.";
		}
		else if(password.equals("") || password == null) {
			validity = "Password is mandatory field.";
		}
		else if(confirmPassword.equals("") || confirmPassword == null) {
			validity = "Confirm Password is mandatory field.";
		}
		else if(!confirmPassword.equals(password)) {
			validity = "Password and Confirm Password don't match. Please verify.";
		}
		else if(address.equals("") || address == null) {
			validity = "Address is mandatory field.";
		}
		else {
			validity = "success";
		}
		
		return validity;
	}

	public boolean doesUserIdExists(String userId) {
		Optional<User> user  = userRepository.findById(userId);
		if(user != null && !user.isEmpty()) {
			System.out.println(user);
			return true;
		}
//		System.out.println(user);
		return false;
	}
	
	
}
