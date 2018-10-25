package com.example.springboot.controller;

import com.example.springboot.module.Teacher;
import com.example.springboot.service.TeacherService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/selectTeacherList")
    private List<Teacher> selectTeacherList() {
        List<Teacher> teacherList = teacherService.selectTeacherList();
        return teacherList;
    }
}
