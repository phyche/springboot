package com.example.springboot.module.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 维修品归还类型
 */
public enum EnumReturnPoolType {
    REPAIRED(1, "已修整"),
    NOT_REPAIRD(2, "无法修整"),

    ;
    private Integer id;
    private String description;

    public static String getDescById(int id) {
        EnumReturnPoolType result = getById(id);
        if (result != null) {
            return result.getDescription();
        }
        return "";
    }

    public static EnumReturnPoolType getById(int id) {
        for (EnumReturnPoolType item : EnumReturnPoolType.values()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
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

    EnumReturnPoolType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
