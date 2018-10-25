package com.example.springboot.service;

import com.example.springboot.module.DisputeType;

import java.util.List;

public interface DisputeTypeService {
    public List<DisputeType> selectAll();

    public void add(DisputeType disputeType);

    public void deleteByIds(String[] ids);
}
