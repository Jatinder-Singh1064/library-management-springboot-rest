package com.library.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;
	
	//	Method for getting user by id
	public User getUserById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseGet(null);
	}
	
	//	Method for getting all the admin users
	public List<User> getAllAdminUsers() {
		List<User> users = new ArrayList<>();
		List<User> adminUsers = new ArrayList<>();
		users = (List<User>) userRepository.findAll();
		for(User user:users) {
			if(user.getUserType().equals("admin")) {
				adminUsers.add(user);
			}
		}
		return adminUsers;
	}
	
	//	Method for getting all the customer users
	public List<User> getAllCustomerUsers() {
		List<User> users = new ArrayList<>();
		List<User> customerUsers = new ArrayList<>();
		users = (List<User>) userRepository.findAll();
		for(User user:users) {
			if(user.getUserType().equals("customer")) {
				customerUsers.add(user);
			}
		}
		return customerUsers;
	}
	
//	Deleting a user by username
	public String deleteAdminUser(String id) {
		User user = getUserById(id);
		userRepository.deleteById(id);
		return user.getUserType();
	}
	
	// 	Method for adding a student into database
	public void addUser(User user) {
		userRepository.save(user);
	}
	
	public void updateUserById(User updatedUser) {
		// TODO Auto-generated method stub
//		int resourceId = updatedBook.getResourceId();
		
		userRepository.save(updatedUser);	
	}
//	
//	//	Deleting a student by id
//	public void deleteStudent(String id) {
//		Optional<Student> student  = studentRepository.findById(id);
//		if(student != null) {
//			studentRepository.deleteById(id);
//		}
//	}
}
