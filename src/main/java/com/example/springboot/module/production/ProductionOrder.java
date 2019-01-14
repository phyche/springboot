package com.example.springboot.module.production;

import com.example.springboot.util.DateUtil;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生产任务单 实体
 * Created by lichengzhen on 2018/4/14.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrder implements Serializable {
    private static final long serialVersionUID = 1901890350542504825L;

    private String id;
    // 状态
    private ProductionOrderStatus status;
    // 投产机构 id
    private String orgId;
    // 业务类型
    private ProductionBiz bizType;
    // 投产类型
    private ProductionType produceType;
    // 预计开始时间
    private Date expectedStartTime;
    // 预计结束时间
    private Date expectedFinishTime;

    // 通用属性
    private Long createPerson;
    private Long updatePerson;
    private Date createTime;
    private Date updateTime;

    /** 子 属性 */
    // 投产物料明细
    private List<ProductionOrderItem> items;
    // 操作记录
    private Map<OperateLogSubType, OperateLog> operateLogs;
    // 名下的修整单号集合
    private List<String> repairIds;

    /** vo 属性 */
    // 出货物品名称
    private String produceMacName;
    // 机型名称
    private String thirdModelName;
    // 操作备注，主要用于存储申请时填写的备注
    private String remark;

    public String getStatusDesc() {
        return status != null ? status.getDescription() : "";
    }

    public String getBizTypeDesc() {
        return bizType != null ? bizType.getDescription() : "";
    }

    public String getProduceTypeDesc() {
        return produceType != null ? produceType.getDescription() : "";
    }

    public String getExpectedStartTimeStr() {
        return expectedStartTime != null ? DateUtil.dateToDateString(expectedStartTime) : null;
    }

    public String getExpectedFinishTimeStr() {
        return expectedFinishTime != null ? DateUtil.dateToDateString(expectedFinishTime) : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductionOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ProductionOrderStatus status) {
        this.status = status;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ProductionBiz getBizType() {
        return bizType;
    }

    public void setBizType(ProductionBiz bizType) {
        this.bizType = bizType;
    }

    public ProductionType getProduceType() {
        return produceType;
    }

    public void setProduceType(ProductionType produceType) {
        this.produceType = produceType;
    }

    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Date getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Date expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public Long getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Long createPerson) {
        this.createPerson = createPerson;
    }

    public Long getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(Long updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ProductionOrderItem> getItems() {
        return items;
    }

    public void setItems(List<ProductionOrderItem> items) {
        this.items = items;
    }

    public Map<OperateLogSubType, OperateLog> getOperateLogs() {
        return operateLogs;
    }

    public void setOperateLogs(Map<OperateLogSubType, OperateLog> operateLogs) {
        this.operateLogs = operateLogs;
    }

    public List<String> getRepairIds() {
        return repairIds;
    }

    public void setRepairIds(List<String> repairIds) {
        this.repairIds = repairIds;
    }

    public String getProduceMacName() {
        return produceMacName;
    }

    public void setProduceMacName(String produceMacName) {
        this.produceMacName = produceMacName;
    }

    public String getThirdModelName() {
        return thirdModelName;
    }

    public void setThirdModelName(String thirdModelName) {
        this.thirdModelName = thirdModelName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
