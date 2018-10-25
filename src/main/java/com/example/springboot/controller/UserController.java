package com.example.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.module.User;
import com.example.springboot.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication(scanBasePackages = {"com.example.springboot.service","com.example.springboot.mapper"})
@MapperScan("com.example.springboot.mapper")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userPage")
    public ModelAndView userPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/user/list");

        List<User> list = new ArrayList<>();
        User user = userService.getUserById(1);
        list.add(user);
        user = userService.getUserById(2);
        list.add(user);
        view.addObject("userList",list);
        return view;
    }

    @RequestMapping("/view")
    @ResponseBody
    public String view () {
        User user = userService.getUserById(2);
        return JSONObject.toJSONString(user);
    }

    @RequestMapping("/selectUserList")
    @ResponseBody
    public String selectUserList () {
        List<User> userList = userService.selectUserList();
        return JSONObject.toJSONString(userList);
    }
}
