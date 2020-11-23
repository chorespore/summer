package com.chao.summer.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/session")
public class RedisSeesionController {
    @PutMapping("/{key}")
    String set(@PathVariable String key, String value, HttpServletRequest request) {
        request.getSession().setAttribute(key, value);
        return key + "=" + value;
    }

    @GetMapping("")
    String query(String key, HttpServletRequest request) {
        Object value = request.getSession().getAttribute(key);
        return (String) value;
    }
}
