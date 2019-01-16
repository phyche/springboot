package com.example.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.module.User;
import com.example.springboot.module.production.ProductionOrder;
import com.example.springboot.module.production.ProductionOrderItem;
import com.example.springboot.service.UserService;
import com.example.springboot.service.production.ProductionOrderService;
import com.example.springboot.util.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SpringBootApplication(scanBasePackages = {"com.example.springboot.service","com.example.springboot.mapper"})
@MapperScan("com.example.springboot.mapper")
public class HelloController {

    @Autowired
    private UserService userService;

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
        view.setViewName("vue/test");
        return view;
    }


}
