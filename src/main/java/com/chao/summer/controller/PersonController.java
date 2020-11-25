package com.chao.summer.controller;

import com.chao.summer.dao.PersonRepository;
import com.chao.summer.entity.Person;
import com.chao.summer.exception.ValidationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

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
        Person person = Person.builder()
                .name(keyword).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", matcher1 -> matcher1.contains())
                .withIgnorePaths("age");

        Example<Person> example = Example.of(person, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "age", "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Person> res = personRepository.findAll(example, pageable);
        return res.getContent();
    }

    @GetMapping("name")
    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    @GetMapping("age")
    public List<String> listNamesByAge(int age) {
        return personRepository.listNamesByAge(age);
    }

    @PutMapping("age")
    public void updateAge(long id, int age) {
        personRepository.updateAge(id, age);
    }

    @GetMapping("ageunder")
    public List<Person> ageUnder(int age) {
        if (age < 0) {
            throw new ValidationException("Age cannot be negtive");
//            throw new NotLoginException();
        }
        Specification<Person> specification = (Specification<Person>) (root, query, criteriaBuilder) -> {
            Predicate predicate1 = criteriaBuilder.lessThan(root.get("age"), age);
            query.where(predicate1);
            return null;
        };
        return personRepository.findAll(specification);
    }

    @GetMapping("count")
    public long listNamesByAge() {
        return personRepository.getTotalCount();
    }
}