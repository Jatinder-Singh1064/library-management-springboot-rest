package com.library.management.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;

@Service
public class BookService {


	@Autowired
	private BookRepository bookRepository;
	
	//	Method for getting all the students
//	public List<User> getAllStudents() {
//		List<Student> students = new ArrayList<>();
//		students = (List<Student>) studentRepository.findAll();
//		return students;
//	}
//	
//	//	Method for getting student by id
//	public Student getStudent(String id) {
//		Optional<Student> student = studentRepository.findById(id);
//		return student.orElseGet(null);
//	}
	
	// 	Method for adding a student into database
	public void addBook(Book book) {
		bookRepository.save(book);
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
