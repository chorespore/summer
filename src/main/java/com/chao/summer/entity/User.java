package com.chao.summer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class User {

    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(nullable = true)
    private Timestamp createTime;

}
