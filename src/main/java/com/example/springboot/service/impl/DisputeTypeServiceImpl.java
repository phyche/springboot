//package com.example.springboot.service.impl;
//
//import com.example.springboot.mapper.DisputeTypeDao;
//import com.example.springboot.module.DisputeType;
//import com.example.springboot.service.DisputeTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service("disputeTypeService")
//@Transactional
//public class DisputeTypeServiceImpl implements DisputeTypeService {
//
//    @Autowired
//    private DisputeTypeDao disputeTypeDao;
//
//    @Override
//    public List<DisputeType> selectAll() {
//        return disputeTypeDao.selectAll();
//    }
//
//    @Override
//    public void add(DisputeType disputeType) {
//        disputeTypeDao.add(disputeType);
//    }
//
//    @Override
//    public void deleteByIds(String[] ids) {
//        disputeTypeDao.deleteByIds(ids);
//    }
//}
