package com.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.management.model.Book;
import com.library.management.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/admin/books/addBook")
	public String addBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("userId", HomeController.userId );
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/books/addBookForm";
	}
	
	@PostMapping("/admin/books/confirmation")
	public String confirmAddBook(@ModelAttribute("book") Book book, Model model) {
		bookService.addBook(book);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/books/addBookConfirmation";
	}
	
	@GetMapping("admin/books/deleteBook")
	public String deleteBook(@RequestParam(value = "bookId", required = true) String bookId, Model model) {
		bookService.deleteBook(Integer.parseInt(bookId));
		model.addAttribute("username", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/books";
	}
	
	@GetMapping("admin/books/modifyBook")
	public String modifyBook(@RequestParam(value = "bookId", required = true) String bookId, Model model) {
		Book book = bookService.getBookById(Integer.parseInt(bookId));
		model.addAttribute("book", book);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "admin/books/updateBookForm";
	}
	
	@PostMapping("/admin/books/updateBook/{bookId}")
	public String updateBook(@PathVariable("bookId") String bookId, @ModelAttribute("book") Book book, Model model) {
		book.setBookId(Integer.parseInt(bookId));
		bookService.updateBookById(book);
		model.addAttribute("userId", HomeController.userId);
		if(HomeController.userId.equals(""))
			return "redirect:/";
		else
			return "redirect:/admin/books";
	}
	
}
