package com.example.springboot.module.production;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 生产任务单明细
 * create by lichengzhen in 18-4-23
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderItem implements Serializable {
    private static final long serialVersionUID = -2311528486198941036L;

    private Long id;
    // 生产任务单 id
    private String prodId;
    // 投入物料编号
    private String macNo;
    // 三级机型
    @Deprecated
    private Integer thirdModelId;
    // 投入数量
    private Integer quantity;
    // 实际投入数量
    private Integer actualQuantity;

    private Date createTime;
    private Date updateTime;

    /** vo 属性 */
    // 物料名称
    private String macName;
    // 三级机型名称（只有整机业务有）
    private String thirdModelName;
    // 是否串号管理
    private Boolean isSerial;
    // 生产任务单串号明细
    private List<ProductionOrderItemSn> itemSns;
    // 投产扫入的串号等等
    private List<String> imeis;
    // 统计信息
    private ProductionOrderStatistics statistics;
    // dsn绑定上传的sn集合
    private List<String> uploadSns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getMacNo() {
        return macNo;
    }

    public void setMacNo(String macNo) {
        this.macNo = macNo;
    }

    public Integer getThirdModelId() {
        return thirdModelId;
    }

    public void setThirdModelId(Integer thirdModelId) {
        this.thirdModelId = thirdModelId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
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

    public String getMacName() {
        return macName;
    }

    public void setMacName(String macName) {
        this.macName = macName;
    }

    public String getThirdModelName() {
        return thirdModelName;
    }

    public void setThirdModelName(String thirdModelName) {
        this.thirdModelName = thirdModelName;
    }

    public Boolean getSerial() {
        return isSerial;
    }

    public void setSerial(Boolean serial) {
        isSerial = serial;
    }

    public List<ProductionOrderItemSn> getItemSns() {
        return itemSns;
    }

    public void setItemSns(List<ProductionOrderItemSn> itemSns) {
        this.itemSns = itemSns;
    }

    public List<String> getImeis() {
        return imeis;
    }

    public void setImeis(List<String> imeis) {
        this.imeis = imeis;
    }

    public ProductionOrderStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(ProductionOrderStatistics statistics) {
        this.statistics = statistics;
    }

    public List<String> getUploadSns() {
        return uploadSns;
    }

    public void setUploadSns(List<String> uploadSns) {
        this.uploadSns = uploadSns;
    }
}
