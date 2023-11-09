package com.lib.lib.services;


import com.lib.lib.models.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface PersonService extends UserDetailsService {

    void save(Person person);

    List<Person> findAll();

    Optional<Person> findByUsername(String username);

    void updateProfile(Person person);

    Person findById(Long id);

    void update(Long id, Person newPerson);
}
