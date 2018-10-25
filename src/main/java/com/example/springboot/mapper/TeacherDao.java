package com.example.springboot.mapper;

import com.example.springboot.module.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("teacherDao")
public interface TeacherDao {

    public List<Teacher> selectTeacherList();
}
