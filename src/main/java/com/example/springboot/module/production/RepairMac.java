package com.example.springboot.module.production;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 修整单用料
 * create by lichengzhen in 18-5-29
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RepairMac {
    private Long id;
    // 出入库申请单号
    private String applyNo;
    // 修整单号
    private String repairId;
    // 机构id
    private String orgId;
    // 使用的物料号
    private String macno;
    // 是否串号管理
    private Boolean isSerial;
    // 使用的物料名称
    private String macname;
    // 申请的物料数量
    private Integer quantity;
    // 物料所属人
    private Long person;
    // 物料所属人
    private String personName;
    // 物料维修使用数量
    private Integer usedQuantity;
    // 良品返还数量，即维修领料未使用的数量
    private Integer goodReturnNum;
    // 坏品返还数量，即维修用料已使用的数量
    private Integer badReturnNum;
    // 是否全部归还
    private Boolean isReturned;
    // 是否全部归还描述
    private String isReturnedDesc;
    // 备注
    private String remark;
    private String imei;
    private String sn;
    private String meid;
    // 更换下来的物料号
    private String oldMacno;
    // 更换下来的imei
    private String oldImei;
    // 更换下来的sn
    private String oldSn;

    // 位置号
    private String position;

    private BigDecimal inPrice;

    private Date dateCreated;
    private Date lastUpdated;

    /* vo 属性 */
    // 总费用
    private BigDecimal totalPrice;

    public Boolean getIsReturned() {
        return this.getQuantity() == this.getBadReturnNum() + this.getGoodReturnNum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMacno() {
        return macno;
    }

    public void setMacno(String macno) {
        this.macno = macno;
    }

    public Boolean getSerial() {
        return isSerial;
    }

    public void setSerial(Boolean serial) {
        isSerial = serial;
    }

    public String getMacname() {
        return macname;
    }

    public void setMacname(String macname) {
        this.macname = macname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public Integer getGoodReturnNum() {
        return goodReturnNum;
    }

    public void setGoodReturnNum(Integer goodReturnNum) {
        this.goodReturnNum = goodReturnNum;
    }

    public Integer getBadReturnNum() {
        return badReturnNum;
    }

    public void setBadReturnNum(Integer badReturnNum) {
        this.badReturnNum = badReturnNum;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    public String getIsReturnedDesc() {
        return isReturnedDesc;
    }

    public void setIsReturnedDesc(String isReturnedDesc) {
        this.isReturnedDesc = isReturnedDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getOldMacno() {
        return oldMacno;
    }

    public void setOldMacno(String oldMacno) {
        this.oldMacno = oldMacno;
    }

    public String getOldImei() {
        return oldImei;
    }

    public void setOldImei(String oldImei) {
        this.oldImei = oldImei;
    }

    public String getOldSn() {
        return oldSn;
    }

    public void setOldSn(String oldSn) {
        this.oldSn = oldSn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
