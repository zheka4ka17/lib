package com.lib.lib.controllers;


import com.lib.lib.models.Person;
import com.lib.lib.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;
@Autowired
    public MainController(PasswordEncoder passwordEncoder, PersonService personService) {
        this.passwordEncoder = passwordEncoder;
    this.personService = personService;
}


   @GetMapping
   public String index(){
    return "index";
   }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("person", new Person());
        return "person/registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@ModelAttribute("person") Person person) {

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personService.save(person);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(){
        return "person/login";

    }
}
