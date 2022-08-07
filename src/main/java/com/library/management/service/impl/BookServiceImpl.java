package com.library.management.service.impl;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void saveBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        bookRepository.save(book);
    }

    @Override
    public Book findBookById(long bookId) {
        Book book = bookRepository.findById(bookId).get();
        return book;
    }

    @Override
    public List<Book> findAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String title, String author) {
        List<Book> searchedBooks = new ArrayList<Book>();

        if (title != null && author == null) {
            searchedBooks = getBookByTitle(title);
        } else if (title == null && author != null) {
            searchedBooks = getBookByAuthor(author);
        } else if (title != null && author != null) {
            searchedBooks = getBookByTitleAndAuthor(title, author);
        }
        return searchedBooks;
    }

    private List<Book> getBookByTitleAndAuthor(String title, String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                books.add(book);
            }
        }
        return books;
    }

    private List<Book> getBookByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                books.add(book);
            }
        }
        return books;
    }

    private List<Book> getBookByTitle(String title) {
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
