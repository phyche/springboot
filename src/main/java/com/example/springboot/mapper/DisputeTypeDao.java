//package com.example.springboot.mapper;
//
//import com.example.springboot.module.DisputeType;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.InsertProvider;
//import org.apache.ibatis.annotations.Result;
//import org.apache.ibatis.annotations.Results;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//import org.apache.ibatis.annotations.UpdateProvider;
//import org.apache.ibatis.type.JdbcType;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository("disputeTypeDao")
//public interface DisputeTypeDao {
//
//    public List<DisputeType> selectAll();
//
//    public void add(DisputeType disputeType);
//
//    public void deleteByIds(String[] ids);
//}