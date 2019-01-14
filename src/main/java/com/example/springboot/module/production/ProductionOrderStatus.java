package com.example.springboot.module.production;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * 生产任务单状态枚举
 * Created by sengzin on 2018/4/14.
 */
public enum ProductionOrderStatus {
    ALL(0, "全部"),
    PENDING_AUDIT(1, "待审核"),
    PENDING_PRODUCE(2, "待投产"),
    PENDING_FINISH(3, "待完结"),
    REJECTED(4, "已驳回"),
    FINISHED(5, "已完结"),
    CANCELED(6, "已取消")
    ;
    private static final Map<String, ProductionOrderStatus> INDEX_ID = Stream.of(ProductionOrderStatus.values()).collect(toMap(e -> String.valueOf(e.getId()), identity()));
    private Integer id;
    private String description;

    @JsonValue
    public Integer getId() {
        return this.id;
    }

    @JsonCreator
    public static ProductionOrderStatus getById(String id) {
        return INDEX_ID.get(id);
    }

    ProductionOrderStatus(Integer id, String description) {
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
