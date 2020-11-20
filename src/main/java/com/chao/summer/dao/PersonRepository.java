package com.chao.summer.dao;

import com.chao.summer.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    List<Person> findAllByName(String name);

    @Query(value = "SELECT count(id) FROM person", nativeQuery = true)
    long getTotalCount();

    @Query("select p.name from Person p where p.age=:age")
    List<String> listNamesByAge(int age);

    @Modifying
    @Transactional
    @Query("UPDATE Person p SET p.age = ?2 WHERE p.id = ?1")
    void updateAge(long id, int age);
}
