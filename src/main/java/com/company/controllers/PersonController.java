package com.company.controllers;

import com.company.models.Book;
import com.company.models.Person;
import com.company.services.BookService;
import com.company.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/person")
public class PersonController {


    private final PersonService personService;
    private final BookService bookService;
    private final PasswordEncoder passwordEncoder;
@Autowired
    public PersonController(PasswordEncoder passwordEncoder, PersonService personService, BookService bookService, PasswordEncoder passwordEncoder1) {
    this.personService = personService;
    this.bookService = bookService;
    this.passwordEncoder = passwordEncoder1;
}

    @GetMapping("/myRoom")
    public String myRoom(Model model, Principal principal){
    if(principal==null)
        throw new RuntimeException();
    Person person=personService.findByUsername(principal.getName()).get();
    model.addAttribute("person", person);

    model.addAttribute("books", person.getBooks());

    return "person/myRoom";
    }

    @GetMapping("edit/{id}")
    public String editPerson(Model model, @PathVariable("id") Long id){

    Person person=personService.findById(id);
        model.addAttribute("person", person);
     return "person/edit";
    }

    @PostMapping("edit/{id}")
    public String doEdit(@PathVariable("id") Long id, @ModelAttribute("person") Person newPerson){
    Person person= personService.findById(id);
    person.setName(newPerson.getName());
    person.setSurname(newPerson.getSurname());
    person.setAddress(newPerson.getAddress());
    person.setPhoneNumber(newPerson.getPhoneNumber());


    personService.update(id, person);

    return "redirect:/person/myRoom";
    }

    @GetMapping("delete-book/{id}")
    public String deleteBook(@PathVariable("id") Long id, Principal principal){
    Person person= personService.findByUsername(principal.getName()).get();
    person.getBooks().remove(bookService.findById(id));
     Book book= bookService.findById(id);
     book.setOwner(null);

     personService.save(person);
     bookService.save(book);

    return "redirect:/person/myRoom";
    }








}
