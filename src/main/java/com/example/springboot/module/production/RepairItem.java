package com.example.springboot.module.production;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author : cylion
 * @Date : 18-5-3 16:54
 * @Description : 维修物料明细
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RepairItem {
    private long id;
    private String repairId; // 维修单id
    private String macno; // 物料编号
    private String macName; // 物料名称
    private int assignNum; // 分配数量
    private int goodReturnNum; // 良品返还数量
    private int badReturnNum; // 坏品返还数量
    private Date lastReturnGoodTime; // 良品最后返还时间
    private Date lastReturnBadTime; // 坏品最后返还时间
    private int status; // 状态(0:未全部返还,1:已全部返还)
    private String orgId; // 机构id
    private Date createTime;
    private long updatePerson;
    private Date updateTime;

    // 串号明细
    private List<RepairItemSn> sns;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getMacno() {
        return macno;
    }

    public void setMacno(String macno) {
        this.macno = macno;
    }

    public String getMacName() {
        return macName;
    }

    public void setMacName(String macName) {
        this.macName = macName;
    }

    public int getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(int assignNum) {
        this.assignNum = assignNum;
    }

    public int getGoodReturnNum() {
        return goodReturnNum;
    }

    public void setGoodReturnNum(int goodReturnNum) {
        this.goodReturnNum = goodReturnNum;
    }

    public int getBadReturnNum() {
        return badReturnNum;
    }

    public void setBadReturnNum(int badReturnNum) {
        this.badReturnNum = badReturnNum;
    }

    public Date getLastReturnGoodTime() {
        return lastReturnGoodTime;
    }

    public void setLastReturnGoodTime(Date lastReturnGoodTime) {
        this.lastReturnGoodTime = lastReturnGoodTime;
    }

    public Date getLastReturnBadTime() {
        return lastReturnBadTime;
    }

    public void setLastReturnBadTime(Date lastReturnBadTime) {
        this.lastReturnBadTime = lastReturnBadTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(long updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<RepairItemSn> getSns() {
        return sns;
    }

    public void setSns(List<RepairItemSn> sns) {
        this.sns = sns;
    }
}
