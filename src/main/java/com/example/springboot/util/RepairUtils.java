package com.example.springboot.util;

import com.example.springboot.module.production.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author : cylion
 * @Date : 18-5-11 11:18
 * @Description :
 */
public class RepairUtils {

    // 主板的状态校验集合，key代表当前状态，value代表可以流转的下个状态
    private static Map<EnumRepairStatus, List<EnumRepairStatus>> mainBoardStatusMap;

    // 整机的状态校验集合，key代表当前状态，value代表可以流转的下个状态
    private static Map<EnumRepairStatus, List<EnumRepairStatus>> wholeMachineStatusMap;

    // 快速维修的状态校验集合，key代表当前状态，value代表可以流转的下个状态
    private static Map<EnumRepairStatus, List<EnumRepairStatus>> fastRepairStatusMap;

    private static EnumSet<EnumRepairStatus> CAN_TURE_OUT_EXCEP_STATUS = EnumSet.of(EnumRepairStatus.PENDING_CHECK_EXCEPTION,
            EnumRepairStatus.PENDING_REPAIR_EXCEPTION,
            EnumRepairStatus.PENDING_WRITE_EXCEPTION,
            EnumRepairStatus.PENDING_PRE_FINAL_CHECK_EXCEPTION,
            EnumRepairStatus.PENDING_FINAL_CHECK_EXCEPTION,
            EnumRepairStatus.PENDING_PACK_EXCEPTION,
            EnumRepairStatus.PENDING_QUALITY_CHECK_EXCEPTION,
            EnumRepairStatus.PENDING_RECEIVE_EXCEPTION);

    private static EnumSet<EnumRepairStatus> REPAIR_NEXT_STATUS_SET = EnumSet.of(
            EnumRepairStatus.PENDING_WRITE,
            EnumRepairStatus.PENDING_FINAL_CHECK,
            EnumRepairStatus.PENDING_PRE_FINAL_CHECK,
            EnumRepairStatus.PENDING_CHECK,
            EnumRepairStatus.PENDING_RETURN);

    static{
        mainBoardStatusMap = new HashMap<>();
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_ENGINER_RECEIVE, Arrays.asList(EnumRepairStatus.PENDING_CHECK));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_CHECK, Arrays.asList(EnumRepairStatus.PENDING_REPAIR));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_REPAIR, Arrays.asList(EnumRepairStatus.PENDING_WRITE));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_WRITE, Arrays.asList(EnumRepairStatus.PENDING_CHECK, EnumRepairStatus.PENDING_FINAL_CHECK));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_FINAL_CHECK, Arrays.asList(EnumRepairStatus.PENDING_CHECK, EnumRepairStatus.PENDING_RETURN));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_RETURN, Arrays.asList(EnumRepairStatus.PENDING_RECEIVE));
        mainBoardStatusMap.put(EnumRepairStatus.PENDING_RECEIVE, Arrays.asList(EnumRepairStatus.RECEIVED_END));

        wholeMachineStatusMap = new HashMap<>();
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_ENGINER_RECEIVE, Arrays.asList(EnumRepairStatus.PENDING_CHECK));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_CHECK, Arrays.asList(EnumRepairStatus.PENDING_REPAIR));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_REPAIR, Arrays.asList(EnumRepairStatus.PENDING_WRITE));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_WRITE, Arrays.asList(EnumRepairStatus.PENDING_CHECK, EnumRepairStatus.PENDING_PRE_FINAL_CHECK));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_PRE_FINAL_CHECK, Arrays.asList(EnumRepairStatus.PENDING_CHECK, EnumRepairStatus.PENDING_FINAL_CHECK));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_FINAL_CHECK, Arrays.asList(EnumRepairStatus.PENDING_CHECK, EnumRepairStatus.PENDING_PACK));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_PACK, Arrays.asList(EnumRepairStatus.PENDING_QUALITY_CHECK));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_QUALITY_CHECK, Arrays.asList(EnumRepairStatus.PENDING_RETURN));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_RETURN, Arrays.asList(EnumRepairStatus.PENDING_RECEIVE));
        wholeMachineStatusMap.put(EnumRepairStatus.PENDING_RECEIVE, Arrays.asList(EnumRepairStatus.RECEIVED_END));

        fastRepairStatusMap = new HashMap<>();
        fastRepairStatusMap.put(EnumRepairStatus.PENDING_ENGINER_RECEIVE, Arrays.asList(EnumRepairStatus.PENDING_RETURN));
        fastRepairStatusMap.put(EnumRepairStatus.PENDING_RETURN, Arrays.asList(EnumRepairStatus.PENDING_RECEIVE));
        fastRepairStatusMap.put(EnumRepairStatus.PENDING_RECEIVE, Arrays.asList(EnumRepairStatus.RECEIVED_END));

    }

    public static Boolean isFactoryThree(Repair repair){
        if(StringUtils.isNotBlank(repair.getProdId())){
            return true;
        }
        return false;
    }

    public static Boolean isFastRepair(Repair repair){
        if(repair.getServiceType()== ProductionBiz.FAST.getId().intValue()){
            return true;
        }
        return false;
    }

    // 整机维修
    public static Boolean isMachineRepair(Repair repair){
        if(repair.getServiceType()== ProductionBiz.WHOLE_MACHINE.getId().intValue()){
            return true;
        }
        return false;
    }

    // 主板维修(原本的业务类型就是主板)
    public static Boolean isMainBoardTypeRepair(Repair repair) {
        if (repair.getServiceType() == ProductionBiz.MAIN_BOARD.getId().intValue()) {
            return true;
        }
        return false;
    }

    public static Boolean isNotFastRepair(Repair repair){
        return !isFastRepair(repair);
    }

    /**
     * 是否可以转入异常区
     */
    public static boolean canTurnInExceptionArea(String status) {
        List<String> statusList = new ArrayList<>();
        statusList.add(EnumRepairStatus.PENDING_CHECK.getCode());
        statusList.add(EnumRepairStatus.PENDING_REPAIR.getCode());
        statusList.add(EnumRepairStatus.PENDING_WRITE.getCode());
        statusList.add(EnumRepairStatus.PENDING_PRE_FINAL_CHECK.getCode());
        statusList.add(EnumRepairStatus.PENDING_FINAL_CHECK.getCode());
        statusList.add(EnumRepairStatus.PENDING_PACK.getCode());
        statusList.add(EnumRepairStatus.PENDING_QUALITY_CHECK.getCode());
        statusList.add(EnumRepairStatus.PENDING_RECEIVE.getCode());
        return statusList.contains(status);
    }

    /**
     * 是否可以转出异常区
     */
    public static boolean canTurnOutExceptionArea(String status) {
        List<String> statusList = new ArrayList<>();
        statusList.add(EnumRepairStatus.PENDING_CHECK_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_WRITE_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_PRE_FINAL_CHECK_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_FINAL_CHECK_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_PACK_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_QUALITY_CHECK_EXCEPTION.getCode());
        statusList.add(EnumRepairStatus.PENDING_RECEIVE_EXCEPTION.getCode());
        return statusList.contains(status);
    }

    /**
     * 可转出异常区状态 code 集合
     */
    public static List<String> listCanTurnOutExceptionAreaCode() {
        return CAN_TURE_OUT_EXCEP_STATUS.stream().map(EnumRepairStatus::getCode).collect(toList());
    }

    /**
     * 维修处理阶段可能的下一个状态集合
     */
    public static List<String> listRepairNextStatus() {
        return REPAIR_NEXT_STATUS_SET.stream().map(item -> RepairUtils.mergeInOutingAndNextStatus(item.getCode())).collect(toList());
    }

    /**
     * 根据当前状态得到转入异常区后的状态
     */
    public static String getTurnInExceptionAreaStatus(String status) {
        // 是否可以转入异常区
        if (!RepairUtils.canTurnInExceptionArea(status)) {
            throw Throwables.runtimeException(RetCode.REPAIR_STATUS_OPER_NOTMATCH, status, "turn_in_exception");
        }
        return String.format("%s_exception", status);
    }

    /**
     * 根据当前状态得到转出异常区后的状态
     */
    public static String getTurnOutExceptionAreaStatus(String status) {
        // 是否可以转出异常区
        if (!RepairUtils.canTurnOutExceptionArea(status)) {
            throw Throwables.runtimeException(RetCode.REPAIR_STATUS_OPER_NOTMATCH, status, "turn_out_exception");
        }
        return status.substring(0, status.indexOf("_exception"));
    }

    /**
     * 根据维修单列表得到出入库明细列表
     *//*
    public static List<EasyInOutItemVo> getEasyInOutItemVoList(List<Repair> repairList) {
        if (CollectionUtils.isEmpty(repairList)) {
            return null;
        }
        List<EasyInOutItemVo> resultList = new ArrayList<>();

        // 按macno分组
        Map<String, List<EasyInOutItemVo>> macnoItemVoMap = repairList.stream().map(repair -> {
            EasyInOutItemVo itemVo = new EasyInOutItemVo();
            itemVo.setMacNo(repair.getMacno());
            List<EasyInOutItemSnVo> itemSnVoList = new ArrayList<>();
            EasyInOutItemSnVo itemSnVo = new EasyInOutItemSnVo();
            itemSnVo.setImei(repair.getImei());
            itemSnVo.setSn(repair.getSn());
            itemSnVo.setMeid(repair.getMeid());
            itemSnVoList.add(itemSnVo);
            itemVo.setLstSn(itemSnVoList);
            return itemVo;
        }).collect(Collectors.groupingBy(EasyInOutItemVo::getMacNo));

        // 按macno构建resultList
        macnoItemVoMap.keySet().forEach(macno -> {
            List<EasyInOutItemVo> itemVoList = macnoItemVoMap.get(macno);
            EasyInOutItemVo itemVo = new EasyInOutItemVo();
            itemVo.setMacNo(macno);
            itemVo.setQuantity(itemVoList.size());
            itemVo.setBinUnitInfoVo(null);
            itemVo.setQuantityVo(new EasyInOutItemQuantityVo(itemVo.getQuantity()));
            List<EasyInOutItemSnVo> itemSnVoList = itemVoList.stream().map(EasyInOutItemVo::getLstSn).flatMap(x -> x.stream()).collect(Collectors.toList());
            itemVo.setLstSn(itemSnVoList);
            resultList.add(itemVo);
        });
        return resultList;
    }*/

/*    public static void changeStatusCheck(Repair repair, EnumRepairStatus nextStatus){
        List<EnumRepairStatus> allowNextStatus = null;
        if(isFastRepair(repair)){
            allowNextStatus = fastRepairStatusMap.get(EnumRepairStatus.getStatusByCode(repair.getStatus()));
        }else if(repair.getServiceType()== ProductionBiz.MAIN_BOARD.getId().intValue()){
            allowNextStatus = mainBoardStatusMap.get(EnumRepairStatus.getStatusByCode(repair.getStatus()));
        }else if(repair.getServiceType()== ProductionBiz.WHOLE_MACHINE.getId().intValue()){
            allowNextStatus = wholeMachineStatusMap.get(EnumRepairStatus.getStatusByCode(repair.getStatus()));
        }
        if(allowNextStatus!=null){
            for(EnumRepairStatus item : allowNextStatus){
                if(item.equals(nextStatus)){
                    return;
                }
            }
            throw Throwables.runtimeException(RetCode.REPAIR_STATUS_CHANGE_ILLEGAL);
        }
    }*/

    /**
     * 从修整单获取操作人米聊号集合
     * @return 会过滤 null/0
     */
    /*public static Stream<Long> streamMiliaoFromRepair(Repair repair) {
        return Stream.of(repair.getCheckPerson(),
                repair.getCreatePerson(),
                repair.getRepairPerson(),
                repair.getReturnPerson(),
                repair.getUpdatePerson(),
                repair.getWritePerson(),
                repair.getCurrEngineer(),
                repair.getEngineerReceivePerson(),
                repair.getFinalCheckPerson())
                .filter(Predicates::greaterThanZero);
    }*/

    /**
     * 从单台报表获取所有物料号
     */
    /*public static Stream<String> streamMacnoFromRepairCostSingle(RepairCostSingle report) {
        if (report == null) return Stream.empty();
        Stream.Builder<String> builder = Stream.<String>builder().add(report.getMacno());
        Optional.ofNullable(report.getItems()).ifPresent(items -> items.forEach(item -> builder.add(item.getMacno())));
        return builder.build();
    }*/

    /**
     * 修整单 转 报表
     */
    /*public static RepairExcelVo repairToExcelVo(Repair repair) {
        return RepairExcelVo.builder()
                .repair(repair)
                .orgId(repair.getOrgId())
                .productionOrderId(repair.getProdId())
                .id(repair.getId())
                .originalFault(CollectionUtils.isEmpty(repair.getOldGdFaults()) ? "" : StringUtils.join(repair.getOldGdFaults().stream().map(item -> RepairUtils.faultSplit(item)[1]).collect(Collectors.toList()), ","))
                .originalFaultCode(CollectionUtils.isEmpty(repair.getOldGdFaults()) ? "" : StringUtils.join(repair.getOldGdFaults().stream().map(item -> RepairUtils.faultSplit(item)[0]).collect(Collectors.toList()), ","))
                .actualFault(CollectionUtils.isEmpty(repair.getCheckFaults()) ? "" : StringUtils.join(repair.getCheckFaults().stream().map(item -> RepairUtils.faultSplit(item)[1]).collect(Collectors.toList()), ","))
                .actualFaultCode(CollectionUtils.isEmpty(repair.getCheckFaults()) ? "" : StringUtils.join(repair.getCheckFaults().stream().map(item -> RepairUtils.faultSplit(item)[0]).collect(Collectors.toList()), ","))
                .status(EnumRepairStatus.getStatusByCode(repair.getStatus()))
                .imei(repair.getImei())
                .originalImei(repair.getOriginalImei())
                .checkResult(repair.getCheckResult())
                .currentPerson(repair.getCurrEngineer())
                .checkPerson(repair.getCheckPerson())
                .checkTime(DateUtil.date2str(repair.getCheckTime()))
                .originalMacno(repair.getMacno())
                .repairPerson(repair.getRepairPerson())
                .repairTime(DateUtil.date2str(repair.getRepairTime()))
                .repairResult(repair.getRepairResult())
                .repairType(repair.getIsUseMacDesc())
                .repairMethod(repair.getRepairMethod())
                .writePerson(repair.getWritePerson())
                .writeTime(DateUtil.date2str(repair.getWriteTime()))
                .writeTime(DateUtil.date2str(repair.getWriteTime()))
                .finalCheckPerson(repair.getFinalCheckPerson())
                .finalCheckTime(DateUtil.date2str(repair.getFinalCheckTime()))
                .finishCheckTime(DateUtil.date2str(repair.getQualityCheckTime()))
                .returnTime(DateUtil.date2str(repair.getReturnTime()))
                .weight(repair.getWeight())
                .build();
    }*/

    /**
     * 修整用料 转 成本报表
     */
    /*public static RepairCostExcelVo repairMacToCostExcelVo(RepairMac rm) {
        return RepairCostExcelVo.builder()
                .repairId(rm.getRepairId())
                .useMacno(rm.getMacno())
                .quantity(rm.getUsedQuantity())
                .imei(rm.getImei())
                .inPrice(rm.getInPrice())
                .totalPrice(rm.getTotalPrice())
                .position(rm.getPosition()).build();
    }*/

    /**
     * 单台成本报表 转 成本报表
     */
    /*public static RepairCostExcelVo repairCostSingleToCostExcelVo(RepairCostSingle report, RepairCostSingleItem item) {
        BigDecimal inPrice = new BigDecimal(String.valueOf(item.getInPrice()));
        return RepairCostExcelVo.builder()
                .returnTime(DateUtil.date2str(report.getReturnTime()))
                .orgId(report.getOrgId())
                .repairId(report.getRepairId())
                .prodId(report.getProdId())
                .transferId(report.getTransferId())
                .bizTypeDesc(report.getProdBiz().getDescription())
                .prodTypeDesc(report.getProduceType().getDescription())
                .macno(report.getMacno())
                .imei(report.getImei())
                .consumeTypeDesc(item.getConsumeType().getDescription())
                .useMacno(item.getMacno())
                .position(item.getPosition())
                .quantity(item.getQuantity())
                .repairResult(report.getRepairTypeDesc())
                .inPrice(inPrice)
                .totalPrice(inPrice.multiply(BigDecimal.valueOf(item.getQuantity())))
                .repairCost(new BigDecimal(String.valueOf(report.getAmount())))
                .build();
    }*/

    /**
     * 维修单是否是换颜色修整
     */
    public static boolean changeColor(Repair repair) {
        if (StringUtils.isNotBlank(repair.getOldMacno()) && StringUtils.isNotBlank(repair.getNewMacno()) && !repair.getOldMacno().equals(repair.getNewMacno())) {
            return true;
        }
        return false;
    }

    /**
     * 计算并填入总费用字段
     * @return  identity
     */
    /*public static RepairMac calculateTotalPrice(RepairMac rm) {
        rm.setTotalPrice(rm.getInPrice().multiply(new BigDecimal(rm.getQuantity())));
        return rm;
    }*/

    /**
     * 是否是主板
     */
    public static boolean isMainBoard(Integer serviceType) {
        return ProductionBiz.MAIN_BOARD.getId().equals(serviceType);
    }

    /**
     * 是否符合内返规则,经历过烧号 且 没有选择过主板故障说明是内返
     */
    /*public static boolean isInternalReturn(Repair repair) throws IOException {
        return StringUtils.isNotBlank(repair.getWriteContent()) && !selectedMainBoard(repair);
    }*/

    /**
     * 是否选择过主板故障
     */
    /*public static boolean selectedMainBoard(Repair repair) throws IOException {
        return null != JsonUtil.getObjectByKey(repair.getCheckContent(), EnumJsonKey.SELECTED_MAIN_BOARD.getCode());
    }*/

    /**
     * 是否是主板维修(整机业务选了主板故障)
     */
    public static boolean isMainBoardRepair(Repair repair) throws IOException {
        return null != JsonUtil.getObjectByKey(repair.getCheckContent(), EnumJsonKey.IS_MAIN_BOARD_REPAIR.getCode());
    }

    /**
     * 根据维修单信息得到下一节点状态
     */
    public static EnumRepairStatus getNextStatus(Repair repair) throws IOException {

        // 默认下一个节点是写号
        EnumRepairStatus result = EnumRepairStatus.PENDING_WRITE;

        // 是主板 或 选择过主板故障则不做特殊处理
        /*if (isMainBoard(repair.getServiceType()) || selectedMainBoard(repair)) {
            return result;
        }*/

        // 是终检内返
        if (null != JsonUtil.getObjectByKey(repair.getFinalCheckContent(), EnumJsonKey.RESULT.getCode())) {
            return EnumRepairStatus.PENDING_FINAL_CHECK;
        }

        // 是初检内返
        if (null != JsonUtil.getObjectByKey(repair.getFinalCheckContent(), EnumJsonKey.PRE_FINAL_CHECK_RESULT.getCode())) {
            return EnumRepairStatus.PENDING_PRE_FINAL_CHECK;
        }
        return result;
    }

    /**
     * 换颜色必须物料的物料子类别过滤条件
     *
     * @return
     */
    public static Integer changeColorRequiredMacSubType(List<Integer> macSubTypes) {
        if (org.springframework.util.CollectionUtils.isEmpty(macSubTypes)) {
            return null;
        }
        Set<Integer> macSubTypeSet = changeColorRequiredMacSubTypeSet();
        for (Integer subType : macSubTypes) {
            if (macSubTypeSet.contains(subType)) {
                return subType;
            }
        }
        return null;
    }

    public static Set<Integer> changeColorRequiredMacSubTypeSet() {
        Set<Integer> macSubTypeSet = new HashSet();
        macSubTypeSet.add(10);    // SIM卡托
        macSubTypeSet.add(108);    // 中框
        macSubTypeSet.add(109);    // 后壳
        macSubTypeSet.add(110);    // 音量键
        macSubTypeSet.add(111);    // 电源键
        macSubTypeSet.add(113);    // 指纹识别模组
        macSubTypeSet.add(4);    // 电池盖
        macSubTypeSet.add(51);    // 壳类
        macSubTypeSet.add(112);    // 侧键
        macSubTypeSet.add(19);    // 前壳触摸屏显示组件
        macSubTypeSet.add(52);    // 音学器件类
        return macSubTypeSet;
    }

    // 故障合并, code + "/" + name = string
    public static String faultMerge(String code, String name) {
        return code + "/" + name;
    }

    // 故障拆分, string = code + "/" + name
    public static String[] faultSplit(String codeAndName) {
        return codeAndName.split("/");
    }

    // 正在出入库状态合并下一个状态
    public static String mergeInOutingAndNextStatus(String nextStatusCode) {
        EnumRepairStatus nextStatus = EnumRepairStatus.getStatusByCode(nextStatusCode);
        if (nextStatus == null) {
            throw new IllegalArgumentException();
        }
        return EnumRepairStatus.PENDING_INOUT.getCode() + "&" + nextStatusCode;
    }

    // 正在出入库状态拆分下一个状态
    public static String splitInOutingAndNextStatus(String mergedStatus) {
        String[] status = mergedStatus.split("&");
        if (!EnumRepairStatus.PENDING_INOUT.getCode().equals(status[0])) {
            throw new IllegalArgumentException();
        }
        return status[1];
    }

}
