package com.example.springboot.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.example.springboot.module.StudentNew;

public class StudentNewSqlProvider {

    public String insertSelective(StudentNew record) {
        BEGIN();
        INSERT_INTO("t_student");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getsName() != null) {
            VALUES("s_name", "#{sName,jdbcType=VARCHAR}");
        }
        
        if (record.getStudentCode() != null) {
            VALUES("student_code", "#{studentCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTeacherId() != null) {
            VALUES("teacher_id", "#{teacherId,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(StudentNew record) {
        BEGIN();
        UPDATE("t_student");
        
        if (record.getsName() != null) {
            SET("s_name = #{sName,jdbcType=VARCHAR}");
        }
        
        if (record.getStudentCode() != null) {
            SET("student_code = #{studentCode,jdbcType=VARCHAR}");
        }
        
        if (record.getTeacherId() != null) {
            SET("teacher_id = #{teacherId,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}