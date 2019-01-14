package com.example.springboot.module.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志类型
 * create by lichengzhen in 18-5-3
 */
public enum OperateLogType implements  EnumerableToString {
    FACTORY_PROD_ORDER(1, "factory_prod_order", "修整工厂生产任务单"),
    FACTORY_REPAIR(2, "factory_repair", "修整工厂维修单")
    ;
    private Integer id;
    private String code;
    private String description;

    public static OperateLogType getByCode(String code) {
        for (OperateLogType item : OperateLogType.values()) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }

    OperateLogType(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
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
