package com.chao.summer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/session")
public class RedisSeesionController {

    @GetMapping("")
    String get(String key, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute(key);
        return (String) value;
    }

    @PutMapping("/{key}")
    String set(@PathVariable String key, String value, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return key + "=" + value;
    }

    @DeleteMapping("/{key}")
    String delete(@PathVariable String key, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
        return key + " Removed";
    }

    @PutMapping("timeout/{timeout}")
    String setTimeout(@PathVariable int timeout, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(timeout);
        String respose = "MaxInactiveInterval: " + session.getMaxInactiveInterval();
        log.info(respose);
        return respose;
    }
}
