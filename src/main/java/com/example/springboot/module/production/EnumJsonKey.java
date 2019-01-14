package com.example.springboot.module.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Michael on 2018/6/22.
 */
public enum EnumJsonKey {
    SELECTED_MAIN_BOARD(0, "selectedMainBoard", "是否选过主板故障"),
    IS_MAIN_BOARD_REPAIR(1, "machineInMainBoard", "是否处于主板维修流程(整机业务选了主板故障)"),
    RESULT(2, "result", "处理结果"),
    PRE_FINAL_CHECK_RESULT(3, "preCheckResult", "初检处理结果"),
    CHECK_FAULT_HISTORY(4, "checkFaultHistory", "检测故障历史");

    private Integer id;
    private String code;
    private String description;

    EnumJsonKey(Integer id, String code, String description) {
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
