package com.example.springboot.module.production;

import com.example.springboot.util.DateUtil;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录
 * create by lichengzhen in 18-5-3
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog implements Serializable {
    private static final long serialVersionUID = -7951721442649233689L;
    private Long id;
    private OperateLogType type;
    private OperateLogSubType subType;
    // 传 操作的单号
    private String operateKey;
    private String content;
    private String remark;
    private Date createTime;
    private String createTimeDesc;
    private Long createPerson;
    private String createPersonName;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createTimeDesc = DateUtil.dateToDateString(createTime);
    }
}
