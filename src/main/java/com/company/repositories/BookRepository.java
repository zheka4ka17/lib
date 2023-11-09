package com.lib.lib.repositories;


import com.lib.lib.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
   Book findByISBN(Long isbn);
}
