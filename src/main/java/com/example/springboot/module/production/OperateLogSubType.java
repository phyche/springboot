package com.example.springboot.module.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作记录子类型
 * create by lichengzhen in 18-5-4
 */
public enum OperateLogSubType implements EnumerableToString {
    /* 通用 */
    INSERT(1, "insert", "添加"),
    UPDATE(2, "update", "更新"),
    DELETE(3, "delete", "删除"),
    DISABLE(4, "disable", "禁用"),
    ACTIVATE(5, "activate", "重新启用"),
    PUBLISH(6, "publish", "发布"),
    AUDIT(7, "audit", "审核"),
    CANCEL(8, "cancel", "取消"),

    // 新建通用 insert
    /* 生产任务单业务操作 */
    PRODUCE(9, "produce", "投产"),
    FINISH(10, "finish", "完结")
    ;
    private Integer id;
    private String code;
    private String description;

    OperateLogSubType(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
