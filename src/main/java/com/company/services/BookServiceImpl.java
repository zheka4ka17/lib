package com.lib.lib.services;


import com.lib.lib.models.Book;
import com.lib.lib.models.Person;
import com.lib.lib.repositories.BookRepository;
import com.lib.lib.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
@Autowired
    public BookServiceImpl(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
    this.personRepository = personRepository;
}

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book  findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public  Book findByISBN(Long ISBN) {
        return bookRepository.findByISBN(ISBN);
    }

    @Override
    public List<Book> findAllAccessibleBooks() {
    List<Book> allAccessibleBooks = new ArrayList<>();
    List<Book> allBooks= bookRepository.findAll();
    for (Book book: allBooks){
        if(book.getOwner()==null)
        allAccessibleBooks.add(book);}
        return allAccessibleBooks ;
    }

    @Transactional
    @Override
    public void save(Book book) {
    bookRepository.save(book);

    }

    @Transactional
    @Override
    public void update(Book newBook, Long id) {
      Book book= bookRepository.findById(id).get();
      book.setDescription(newBook.getDescription());
      book.setUpdated(LocalDateTime.now());
      book.setGenre(newBook.getGenre());
      bookRepository.save(book);

}
    @Transactional
    @Override
    public void deleteBookById(Long id) {
    bookRepository.deleteById(id);

    }

    @Transactional
    public void addBookToPerson(Long bookId, Principal principal){
    Person person=personRepository.findByUsername(principal.getName()).get();
    Book book= bookRepository.findById(bookId).get();
    book.setOwner(person);
    person.getBooks().add(book);
    bookRepository.save(book);
    personRepository.save(person);


    }




}

