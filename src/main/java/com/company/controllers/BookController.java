package com.lib.lib.controllers;



import com.lib.lib.models.Book;
import com.lib.lib.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {

private final BookService bookService;
@Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }




    @GetMapping("/all")
    public String allBooks(Model model){
    model.addAttribute("books", bookService.findAll());
    return "book/all";

    }
    @GetMapping("/search")
    public String search(){

    return "book/search";
    }

    @PostMapping("/search")
    public String resultSearchById(Model model, @RequestParam(value = "query") String query){
     if(!(bookService.findById(Long.valueOf(query))==null)) {
         Book book=bookService.findById(Long.valueOf(query));
            model.addAttribute("book", book );
            if(book.getOwner()==null){
                model.addAttribute("available", true);
            }
        }
   else if(!(bookService.findByISBN(Long.valueOf(query))==null)) {
         Book book=bookService.findByISBN(Long.valueOf(query));
            model.addAttribute("book", bookService.findByISBN(Long.valueOf(query)));
         if(book.getOwner()==null){
             model.addAttribute("available", true);
         }
        }


        return "book/search";

    }

    @GetMapping("/new")
    public String newBook(Model model){
    model.addAttribute("newBook", new Book());
    return "book/new_book";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("newBook") Book newBook){
        System.out.println("gedzbgfb");
        bookService.save(newBook);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable("id") Long id){
    bookService.deleteBookById(id);
    return "redirect:/books/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("book", bookService.findById(id) );
        return "book/update";
    }
    @PostMapping("/edit/{id}")
    public String makeUpdate(@PathVariable("id") Long id, @ModelAttribute("book") Book newBook) {

        bookService.update(newBook, id);

        return "redirect:/books/all";

    }
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") Long id, Model model){
    model.addAttribute("book", bookService.findById(id));
    if(bookService.findById(id).getOwner()==null)
        model.addAttribute("available", true);
    else model.addAttribute("not_available", true);
    return "book/show";

}

 @GetMapping("available-books")
 public String availableBooks(Model model){
  List<Book> allBooks= bookService.findAll();
  List<Book> availableBooks=new ArrayList<>();
  for(Book book : allBooks )
      if(book.getOwner()==null)
      availableBooks.add(book);

  model.addAttribute("books", availableBooks);
  return "book/available_books";
 }
    @GetMapping("/add-book/{id}")
    public String addBookToOrder(@PathVariable("id") Long id, Model model, Principal principal) {
    bookService.addBookToPerson(id,principal);
    return "redirect:/books/available-books";

    }




}
