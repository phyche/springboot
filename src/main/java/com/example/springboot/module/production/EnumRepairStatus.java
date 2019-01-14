package com.example.springboot.module.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : cylion
 * @Date : 18-5-3 17:48
 * @Description : 维修单状态
 */

public enum EnumRepairStatus  {

    PENDING_ENGINER_RECEIVE(1, "pending_enginer_receive", "待工程师接收"),
    PENDING_CHECK(2, "pending_check", "待检测"),
    PENDING_REPAIR(3, "pending_repair", "待维修"),
    PENDING_WRITE(4, "pending_write", "待写号"),
    PENDING_PRE_FINAL_CHECK(5, "pending_pre_final_check", "待初检"),
    PENDING_FINAL_CHECK(6, "pending_final_check", "待终检"),
    PENDING_PACK(7, "pending_pack", "待包装"),
    PENDING_QUALITY_CHECK(8,"pending_quality_check", "待质检"),
    PENDING_RETURN(9, "pending_return", "待返还"),
    PENDING_RECEIVE(10, "pending_receive", "待接收"),
    RECEIVED_END(11, "received_end", "接收完成"),
    PENDING_CHECK_EXCEPTION(12, "pending_check_exception", "检测异常待处理"),
    PENDING_REPAIR_EXCEPTION(13, "pending_repair_exception", "维修异常待处理"),
    PENDING_WRITE_EXCEPTION(14, "pending_write_exception", "写号异常待处理"),
    PENDING_PRE_FINAL_CHECK_EXCEPTION(14, "pending_pre_final_check_exception", "初检异常待处理"),
    PENDING_FINAL_CHECK_EXCEPTION(15, "pending_final_check_exception", "终检异常待处理"),
    PENDING_PACK_EXCEPTION(16, "pending_pack_exception", "包装异常待处理"),
    PENDING_INOUT(19,"pending_inout", "正在做出入库"),
    // 驻场质检、接收不需要转异常了
    @Deprecated
    PENDING_QUALITY_CHECK_EXCEPTION(17, "pending_quality_check_exception", "质检异常待处理"),
    // 驻场质检、接收不需要转异常了
    @Deprecated
    PENDING_RECEIVE_EXCEPTION(18, "pending_receive_exception", "接收异常待处理");

    private Integer id;
    private String code;
    private String description;

    private EnumRepairStatus(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public static String getDescByCode(String code) {
        EnumRepairStatus status = getStatusByCode(code);
        return status==null?"":status.getDescription();
    }

    public static EnumRepairStatus getStatusByCode(String code) {
        for (EnumRepairStatus item : EnumRepairStatus.values()) {
            if (item.code.equals(code)) {
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
