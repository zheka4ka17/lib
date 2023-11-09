package com.lib.lib.services;


import com.lib.lib.models.Book;

import java.security.Principal;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book findByISBN(Long ISBN);
    List<Book> findAllAccessibleBooks();

    void deleteBookById(Long id);
    void update(Book newBook, Long id);

    void save(Book book);
    void addBookToPerson(Long bookId, Principal principal);
}
