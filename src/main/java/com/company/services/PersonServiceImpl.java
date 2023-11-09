package com.company.services;

import com.company.repositories.PersonRepository;
import com.company.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
@Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void save(Person person) {
    personRepository.save(person);

    }

    @Override
    public List<Person> findAll() {
    return personRepository.findAll();

    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateProfile(Person newPerson) {
    Person person= personRepository.findByUsername(newPerson.getUsername()).get();
    newPerson.setId(person.getId());
    personRepository.save(newPerson);

    }

    @Override
    public Person findById(Long id) {
    return personRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, Person newPerson) {

        newPerson.setId(id);
        personRepository.save(newPerson);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Person> person= personRepository.findByUsername(username);
        if(person.isEmpty())
            throw new UsernameNotFoundException("Person " + username + " not found");


        return new PersonDetails(person.get());
    }
}
