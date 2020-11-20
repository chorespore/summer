package com.chao.summer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true, length = 20)
    private String name;

    private int age;
}