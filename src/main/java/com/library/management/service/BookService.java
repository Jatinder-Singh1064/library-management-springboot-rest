package com.library.management.service;

import com.library.management.model.Book;

import java.util.List;

public interface BookService {

    void saveBook(Book book);

    void saveBookById(Long bookId);

    Book findBookById(long bookId);

    List<Book> findAllBooks();

    List<Book> searchBooks(String title, String author);

    void deleteBookById(long bookId);
}
