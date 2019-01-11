package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        //Hello Spring Boot!
        return "index";
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }

    @RequestMapping("/vue")
    public ModelAndView vue() {
        ModelAndView view = new ModelAndView();
        view.setViewName("vue/example");
        return view;
    }

    @RequestMapping("/vue2")
    public ModelAndView vue2() {
        ModelAndView view = new ModelAndView();
        view.setViewName("vue/example2");
        return view;
    }
}
