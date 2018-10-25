package com.example.springboot.service.impl;

import com.example.springboot.mapper.TeacherDao;
import com.example.springboot.module.Teacher;
import com.example.springboot.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public List<Teacher> selectTeacherList() {
        return teacherDao.selectTeacherList();
    }
}
