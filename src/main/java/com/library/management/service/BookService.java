package com.library.management.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;

@Service
public class BookService {


	@Autowired
	private BookRepository bookRepository;
	
//		Method for getting all the students
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		books = (List<Book>) bookRepository.findAll();
		return books;
	}
	
	//	Method for getting book by id
	public Book getBookById(int id) {
		Optional<Book> student = bookRepository.findById(id);
		return student.orElseGet(null);
	}
	
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
	//	Deleting a book by id
	public void deleteBook(int id) {
		bookRepository.deleteById(id);
	}

	public void updateBookById(Book updatedBook) {
		// TODO Auto-generated method stub
//		int resourceId = updatedBook.getResourceId();
		
		bookRepository.save(updatedBook);
		
	}

}
