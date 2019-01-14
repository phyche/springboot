package com.example.springboot.module.production;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 生产任务单业务类型枚举
 * 数据库定义类型为 tinyint，故 id 不可超过 255
 * Created by lichengzhen on 2018/4/14.
 */
public enum ProductionBiz {
    WHOLE_MACHINE(1, "整机业务"),
    MAIN_BOARD(3, "主板业务"),
    FAST(5, "快速业务");
    private Integer id;
    private String description;
    // id 索引
    private static final Map<String, ProductionBiz> INDEX_ID = Stream.of(ProductionBiz.values()).collect(Collectors.toMap(x -> String.valueOf(x.getId()), x -> x));
    /**
     * 根据 id 获取
     * 反序列化
     */
    @JsonCreator
    public static ProductionBiz getById(Integer id) {
        return getById(String.valueOf(id));
    }

    /**
     * 根据 id 获取
     */
    public static ProductionBiz getById(String id) {
        return INDEX_ID.get(id);
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    ProductionBiz(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
