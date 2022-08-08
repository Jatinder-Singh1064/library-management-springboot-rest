package com.library.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;
	
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
//	
//	//	Method for getting student by id
//	public Student getStudent(String id) {
//		Optional<Student> student = studentRepository.findById(id);
//		return student.orElseGet(null);
//	}
	
	// 	Method for adding a student into database
	public void addUser(User user) {
		userRepository.save(user);
	}
	
	//	Method for updating student into database (only if it exists)
//	public Student updateStudent(String id, Student student) {
//		Optional<Student> student1  = studentRepository.findById(id);
//		if(student1 != null) {
//			studentRepository.save(student);
//		}
//		Optional<Student> student2 = studentRepository.findById(id);
//		return student2.orElseGet(null);
//	}
//	
//	//	Deleting a student by id
//	public void deleteStudent(String id) {
//		Optional<Student> student  = studentRepository.findById(id);
//		if(student != null) {
//			studentRepository.deleteById(id);
//		}
//	}
}
