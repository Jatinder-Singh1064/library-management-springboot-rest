package com.library.management.service;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public List<Book> findRelatedBooks(Book book) {
		// TODO Auto-generated method stub
		HashSet<Book> relatedBooks = new HashSet<>();
		List<Book> books = getAllBooks();
		for(Book b:books) {
			if(b.getIsbn().equals(book.getIsbn())) {
				relatedBooks.add(b);
			}
			if(b.getAuthor().contains(book.getAuthor()) && !book.getAuthor().equals("")) {
				relatedBooks.add(b);
			}
			if(b.getCategory().contains(book.getCategory()) && !book.getCategory().equals("")) {
				relatedBooks.add(b);
			}
			if(b.getTitle().contains(book.getTitle()) && !book.getTitle().equals("")) {
				relatedBooks.add(b);
			}
			if(b.getPublisher().contains(book.getPublisher()) && !book.getPublisher().equals("")) {
				relatedBooks.add(b);
			}
		}
		List<Book> relatedAllBooks = (ArrayList<Book>)relatedBooks.stream().collect(Collectors.toList());
		return relatedAllBooks;
	}

}
