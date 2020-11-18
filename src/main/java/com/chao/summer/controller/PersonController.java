package com.chao.summer.controller;

import com.chao.summer.dao.PersonRepository;
import com.chao.summer.entity.Person;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("")
    public Person addPerson(Person person) {

        return personRepository.save(person);
    }

    @DeleteMapping("")
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    @GetMapping("")
    public List<Person> findPerson(String name) {
        return personRepository.findAllByName(name);
    }
}