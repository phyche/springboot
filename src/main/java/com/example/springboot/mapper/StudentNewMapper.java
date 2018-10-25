package com.example.springboot.mapper;

import com.example.springboot.module.StudentNew;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface StudentNewMapper {
    @Delete({
        "delete from t_student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_student (id, s_name, ",
        "student_code, teacher_id, ",
        "user_id)",
        "values (#{id,jdbcType=INTEGER}, #{sName,jdbcType=VARCHAR}, ",
        "#{studentCode,jdbcType=VARCHAR}, #{teacherId,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER})"
    })
    int insert(StudentNew record);

    @InsertProvider(type=StudentNewSqlProvider.class, method="insertSelective")
    int insertSelective(StudentNew record);

    @Select({
        "select",
        "id, s_name, student_code, teacher_id, user_id",
        "from t_student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="s_name", property="sName", jdbcType=JdbcType.VARCHAR),
        @Result(column="student_code", property="studentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="teacher_id", property="teacherId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER)
    })
    StudentNew selectByPrimaryKey(Integer id);

    @UpdateProvider(type=StudentNewSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StudentNew record);

    @Update({
        "update t_student",
        "set s_name = #{sName,jdbcType=VARCHAR},",
          "student_code = #{studentCode,jdbcType=VARCHAR},",
          "teacher_id = #{teacherId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StudentNew record);
}