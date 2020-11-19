package com.chao.summer.controller;

import com.chao.summer.dao.PersonRepository;
import com.chao.summer.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public List<Person> findPerson(String keyword, int page, int size) {
        Person person = new Person();
        person.setName(keyword);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("age");

        Example<Person> example = Example.of(person, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "age", "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Person> res = personRepository.findAll(example, pageable);
        return res.getContent();
    }

    @GetMapping("names")
    public List<String> listNamesByAge(int age) {
        return personRepository.listNamesByAge(age);
    }

    @GetMapping("count")
    public long listNamesByAge() {
        return personRepository.getTotalCount();
    }

    @PutMapping("age")
    public void updateAge(long id, int age) {
        personRepository.updateAge(id, age);
    }
}