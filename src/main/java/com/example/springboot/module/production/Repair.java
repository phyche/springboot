package com.example.springboot.module.production;

import com.example.springboot.util.BasicDataUtils;
import com.example.springboot.util.JsonUtil;
import com.example.springboot.util.RepairUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cylion
 * @Date: 18-5-3 15:01
 * @Description:　维修单实体类
 *
 */
@Slf4j
@Setter
@Getter
@ToString
public class Repair  {
    private String id;
    private String prodId; // 生产任务单号,2.0都为空字符串,3.0肯定有值
    private String groupNo; //　维修单分组号，扩展预留
    private String sn; // sn
    private String imei; // imei
    private String orgId; // 机构代码
    private String status; // 维修单状态
    private String statusDesc; // 维修单状态描述
    private Long createPerson; // 创建人
    private Date createTime; //
    private Long currEngineer; // 当前工程师
    private Long updatePerson; //
    private Date updateTime; //
    private Integer serviceType; // 维修类型1:整机维修\\n2：整机主板维修\\n3：主板维修\\n4：待维修良品维修;在工厂3.0中，这个字段的含义有变化，1整机维修　3主板维修　5快速维修
    private String serviceTypeDesc;
    private Long engineerReceivePerson; // 工程师接收工程师
    private Date engineerReceiveTime; // 工程师接收时间
    private Long checkPerson; // 检测工程师
    private Date checkTime; // 检测时间
    private String checkContent; // 检测内容
    private Long repairPerson; // 维修工程师
    private Date repairTime; // 维修时间
    private String repairContent; // 维修内容
    private Long writePerson; // 写号工程师
    private Date writeTime; // 写号时间
    private String writeContent; // 写号内容
    private String newImei; //
    private Long finalCheckPerson; // 终检工程师
    private Date finalCheckTime; // 终检时间
    private String finalCheckContent; // 终检内容
    private Long returnPerson; // 修整品返还接收人
    private Date returnTime; // 修整品返还时间
    private Integer returnPoolType; // 归还库区类型：1-已修整，2-无法修整，详见　EnumReturnPoolType
    private Integer returnAim; // 返还类型 1：人,2：库
    private Date dateCreated; //
    private Date lastUpdated; //
    private String macno; // 物品编号
    private String macName; // 物品名称
    private Integer colorId; // 物品颜色id
    private String meid; //
    private String originalImei; //
    private String oldImei; //
    private String fsn; // fsn与imei对应关系中的值
    private List<String> oldGdFaults; // 原imei对应工单的故障描述, string格式：code/name
    private List<String> checkFaults; // 实际检测故障, string格式：code/name
    private List<String> checkFaultHistory; // 历史检测故障
    private String checkResult; // 检测结果
    String remark;  // 备注
    Boolean shouldBandNetworkStandard;  // 是否应该绑定网标
    String weight;  // 重量
    private Long qualityCheckPerson;
    private Date qualityCheckTime;
    private String qualityCheckContent;
    private String exceptionRemark; // 异常备注
    private String repairResult;
    private String isUseMacDesc; // 用料维修, 非用料维修
    private String repairMethod; // 维修方法, 更换物料,软件升级,焊接等等

    private String oldMacno;
    private String oldMacName;
    private String newMacno;
    private String newMacName;
    private Boolean machineInMainBoard; // 整机维修正在修主板

    /* 其他 */
    // 生产类型
    private ProductionType prodType;
    // 机构名称
    private String orgName;
    // 二级机型名称
    private String secondModelName;
    // 快速修整，物料和串号明细信息存在items字段;　非快速修整，一个修整单只有一个imei,imei和物料信息存在imei和macno字段
    private List<RepairItem> items;

    public void setStatus(String status){
        this.status = status;
        this.statusDesc = EnumRepairStatus.getDescByCode(status);
    }

    public void setServiceType(int serviceType){
        this.serviceType = serviceType;
        this.serviceTypeDesc = ProductionBiz.getById(serviceType).getDescription();
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
        if (StringUtils.isNotBlank(checkContent)) {
            try {
                List<String> defect = JsonUtil.getObjectListByKey(checkContent, "defect");
                if (defect != null) {
                    this.checkFaults = defect;
                }

                List<String> checkFaultHistory = JsonUtil.getObjectListByKey(checkContent, EnumJsonKey.CHECK_FAULT_HISTORY.getCode());
                if (checkFaultHistory != null) {
                    this.checkFaultHistory = checkFaultHistory;
                }

                List<String> oldDefect = JsonUtil.getObjectListByKey(checkContent, "history");
                if (oldDefect != null) {
                    this.oldGdFaults = oldDefect;
                }

                String machineInMainBoardStr = JsonUtil.getText(checkContent, EnumJsonKey.IS_MAIN_BOARD_REPAIR.getCode());
                if(StringUtils.isNotBlank(machineInMainBoardStr)){
                    this.machineInMainBoard = true;
                }else{
                    this.machineInMainBoard = false;
                }

                this.oldMacno = JsonUtil.getText(checkContent, "result.oldMacno");
                this.oldMacName = JsonUtil.getText(checkContent, "result.oldMacName");
                this.newMacno = JsonUtil.getText(checkContent, "result.newMacno");
                this.newMacName = JsonUtil.getText(checkContent, "result.newMacName");

                this.checkResult = JsonUtil.getText(checkContent, "result.checkResult");
            } catch (Exception e) {
                //log.error("解析检测内容的检测故障结点报错.", e);
            }
        }
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
        if (StringUtils.isNotBlank(repairContent)) {
            try {

                Object result = JsonUtil.getObjectByKey(repairContent, "result");
                if (result != null) {
                    this.repairResult = result.toString();
                }

                if (BasicDataUtils.objToTrue(JsonUtil.getObjectByKey(repairContent, "isUseMac"))) {
                    this.isUseMacDesc = "用料维修";
                } else {
                    this.isUseMacDesc = "非用料维修";
                }

                Map repairContentTypeMap = (Map) JsonUtil.getObjectByKey(repairContent, "type");
                if (repairContentTypeMap != null) {
                    StringBuilder repairMethodDesc = new StringBuilder("");
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("welding"))) {
                        repairMethodDesc.append("扒焊").append(";");
                    }
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("rewelding"))) {
                        repairMethodDesc.append("重焊").append(";");
                    }
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("normalWelding"))) {
                        repairMethodDesc.append("一般焊接").append(";");
                    }
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("bgaWelding"))) {
                        repairMethodDesc.append("BGA焊接").append(";");
                    }
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("cleaning"))) {
                        repairMethodDesc.append("清洁").append(";");
                    }
                    if (BasicDataUtils.objToTrue(repairContentTypeMap.get("softwareUpdate"))) {
                        repairMethodDesc.append("软件升级").append(";");
                    }
                    this.repairMethod = repairMethodDesc.toString();

                }

            } catch (Exception e) {
                //log.error("解析检测内容的检测故障结点报错.", e);
            }
        }
    }

    /*public String getProdTypeDesc() {
        return prodType != null ? prodType.getDescription() : null;
    }*/

    public void setFinalCheckContent(String finalCheckContent) {
        this.finalCheckContent = finalCheckContent;
        if (StringUtils.isNotBlank(finalCheckContent)) {
            try {
                this.weight = (String) JsonUtil.getObjectByKey(finalCheckContent, "pack.weight");
            } catch (Exception e) {
                //log.error("解析终检内容报错.", e);
            }
        }
    }

    /**
     * 是否可以选择主板故障: 是整机业务 且 不是主板维修
     */
    public boolean getCanSelectMainBoardFault() {
        try {
            return ProductionBiz.WHOLE_MACHINE.getId().equals(getServiceType()) && !RepairUtils.isMainBoardRepair(this);
        } catch (IOException e) {
            //log.error("repair.canSelectMainBoardFault报错", e);
            return true;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Long createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCurrEngineer() {
        return currEngineer;
    }

    public void setCurrEngineer(Long currEngineer) {
        this.currEngineer = currEngineer;
    }

    public Long getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(Long updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeDesc() {
        return serviceTypeDesc;
    }

    public void setServiceTypeDesc(String serviceTypeDesc) {
        this.serviceTypeDesc = serviceTypeDesc;
    }

    public Long getEngineerReceivePerson() {
        return engineerReceivePerson;
    }

    public void setEngineerReceivePerson(Long engineerReceivePerson) {
        this.engineerReceivePerson = engineerReceivePerson;
    }

    public Date getEngineerReceiveTime() {
        return engineerReceiveTime;
    }

    public void setEngineerReceiveTime(Date engineerReceiveTime) {
        this.engineerReceiveTime = engineerReceiveTime;
    }

    public Long getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(Long checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public Long getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(Long repairPerson) {
        this.repairPerson = repairPerson;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public Long getWritePerson() {
        return writePerson;
    }

    public void setWritePerson(Long writePerson) {
        this.writePerson = writePerson;
    }

    public Date getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    public String getWriteContent() {
        return writeContent;
    }

    public void setWriteContent(String writeContent) {
        this.writeContent = writeContent;
    }

    public String getNewImei() {
        return newImei;
    }

    public void setNewImei(String newImei) {
        this.newImei = newImei;
    }

    public Long getFinalCheckPerson() {
        return finalCheckPerson;
    }

    public void setFinalCheckPerson(Long finalCheckPerson) {
        this.finalCheckPerson = finalCheckPerson;
    }

    public Date getFinalCheckTime() {
        return finalCheckTime;
    }

    public void setFinalCheckTime(Date finalCheckTime) {
        this.finalCheckTime = finalCheckTime;
    }

    public String getFinalCheckContent() {
        return finalCheckContent;
    }

    public Long getReturnPerson() {
        return returnPerson;
    }

    public void setReturnPerson(Long returnPerson) {
        this.returnPerson = returnPerson;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getReturnPoolType() {
        return returnPoolType;
    }

    public void setReturnPoolType(Integer returnPoolType) {
        this.returnPoolType = returnPoolType;
    }

    public Integer getReturnAim() {
        return returnAim;
    }

    public void setReturnAim(Integer returnAim) {
        this.returnAim = returnAim;
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

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getOriginalImei() {
        return originalImei;
    }

    public void setOriginalImei(String originalImei) {
        this.originalImei = originalImei;
    }

    public String getOldImei() {
        return oldImei;
    }

    public void setOldImei(String oldImei) {
        this.oldImei = oldImei;
    }

    public String getFsn() {
        return fsn;
    }

    public void setFsn(String fsn) {
        this.fsn = fsn;
    }

    public List<String> getOldGdFaults() {
        return oldGdFaults;
    }

    public void setOldGdFaults(List<String> oldGdFaults) {
        this.oldGdFaults = oldGdFaults;
    }

    public List<String> getCheckFaults() {
        return checkFaults;
    }

    public void setCheckFaults(List<String> checkFaults) {
        this.checkFaults = checkFaults;
    }

    public List<String> getCheckFaultHistory() {
        return checkFaultHistory;
    }

    public void setCheckFaultHistory(List<String> checkFaultHistory) {
        this.checkFaultHistory = checkFaultHistory;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getShouldBandNetworkStandard() {
        return shouldBandNetworkStandard;
    }

    public void setShouldBandNetworkStandard(Boolean shouldBandNetworkStandard) {
        this.shouldBandNetworkStandard = shouldBandNetworkStandard;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Long getQualityCheckPerson() {
        return qualityCheckPerson;
    }

    public void setQualityCheckPerson(Long qualityCheckPerson) {
        this.qualityCheckPerson = qualityCheckPerson;
    }

    public Date getQualityCheckTime() {
        return qualityCheckTime;
    }

    public void setQualityCheckTime(Date qualityCheckTime) {
        this.qualityCheckTime = qualityCheckTime;
    }

    public String getQualityCheckContent() {
        return qualityCheckContent;
    }

    public void setQualityCheckContent(String qualityCheckContent) {
        this.qualityCheckContent = qualityCheckContent;
    }

    public String getExceptionRemark() {
        return exceptionRemark;
    }

    public void setExceptionRemark(String exceptionRemark) {
        this.exceptionRemark = exceptionRemark;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public String getIsUseMacDesc() {
        return isUseMacDesc;
    }

    public void setIsUseMacDesc(String isUseMacDesc) {
        this.isUseMacDesc = isUseMacDesc;
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    public String getOldMacno() {
        return oldMacno;
    }

    public void setOldMacno(String oldMacno) {
        this.oldMacno = oldMacno;
    }

    public String getOldMacName() {
        return oldMacName;
    }

    public void setOldMacName(String oldMacName) {
        this.oldMacName = oldMacName;
    }

    public String getNewMacno() {
        return newMacno;
    }

    public void setNewMacno(String newMacno) {
        this.newMacno = newMacno;
    }

    public String getNewMacName() {
        return newMacName;
    }

    public void setNewMacName(String newMacName) {
        this.newMacName = newMacName;
    }

    public Boolean getMachineInMainBoard() {
        return machineInMainBoard;
    }

    public void setMachineInMainBoard(Boolean machineInMainBoard) {
        this.machineInMainBoard = machineInMainBoard;
    }

    public ProductionType getProdType() {
        return prodType;
    }

    public void setProdType(ProductionType prodType) {
        this.prodType = prodType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSecondModelName() {
        return secondModelName;
    }

    public void setSecondModelName(String secondModelName) {
        this.secondModelName = secondModelName;
    }

    public List<RepairItem> getItems() {
        return items;
    }

    public void setItems(List<RepairItem> items) {
        this.items = items;
    }
}
