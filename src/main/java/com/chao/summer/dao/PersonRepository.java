package com.chao.summer.dao;

import com.chao.summer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    void deleteById(Long id);
}
