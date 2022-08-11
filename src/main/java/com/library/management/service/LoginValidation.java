package com.library.management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;

@Service
public class LoginValidation {

	@Autowired
	private UserRepository userRepository;

	private String userType = "";
	
	public String validateUser(User user) {
		String validity = "";
		
		String userId = user.getUserId();
		String password = user.getPassword();
		
		if(userId.equals("") || userId == null) {
			validity = "UserId is mandatory field.";
		}
		else if(doesUserIdExists(userId)) {
			validity = "UserId does not exist. Please register before login.";
		}
		else if(password.equals("") || password == null) {
			validity = "Password is mandatory field.";
		}
		else if(!isValid(userId, password)) {
			validity = "Incorrect UserId/Password.";
		}
		else {
			validity = "success";
		}
		
		return validity;
	}

	private boolean doesUserIdExists(String userId) {
		Optional<User> user  = userRepository.findById(userId);
		if(user != null && !user.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean isValid(String userId, String password) {
		Optional<User> user  = userRepository.findById(userId);
		if(user != null && !user.isEmpty()) {
			if(user.get().getPassword().equals(password)) {
				userType = user.get().getUserType();
				return true;
			}
		}
		return false;
	}
	

	public String getUserType(User user) {
		return userType;
	}
	
}
