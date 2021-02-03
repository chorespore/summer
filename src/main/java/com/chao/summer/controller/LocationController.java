package com.chao.summer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("location")
public class LocationController {

    @GetMapping("f")
    public String forword() {
        return "forward:https://www.baidu.com";
    }

    @GetMapping("r")
    public String redirect1() {
        return "redirect:https://www.baidu.com";
    }

    @GetMapping("r2")
    public void redirect2(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://www.baidu.com");
    }

}
