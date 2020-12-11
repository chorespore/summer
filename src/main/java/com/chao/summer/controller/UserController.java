package com.chao.summer.controller;


import com.chao.summer.entity.User;
import com.chao.summer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Chao
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/summer/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public User findById(Long id) {
        User user = userService.getById(id);
        System.out.println(user);
        return user;
    }

    @GetMapping("list")
    public List<User> list() {
        List<User> users = userService.list();
        userService.list().forEach(System.out::println);
        return users;
    }

    @PostMapping("")
    public boolean create(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }


}

