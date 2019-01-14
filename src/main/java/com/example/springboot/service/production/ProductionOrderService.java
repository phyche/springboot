package com.example.springboot.service.production;

import com.example.springboot.mapper.production.ProductionOrderMapper;
import com.example.springboot.module.production.*;
import com.example.springboot.util.Throwables;
import javafx.scene.paint.Material;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author lichengzhen
 * @Description 生产任务单 srv
 * @Date Created in 2018/4/14.
 */
@Slf4j
@Service
public class ProductionOrderService {
    @Autowired
    private ProductionOrderManager prodManager;
    @Autowired
    private ProductionOrderMapper prodMapper;
    /*@Autowired
    private StorageInterfaceService storageInterfaceService;
    @Autowired
    private BasicDataInterfaceService basicDataInterfaceService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private SrvRepairManager repairManager;*/
    /*@Autowired
    private OperateLogManager operateLogManager;*/
    private static final Boolean FINISHED = Boolean.TRUE;
    private static final Boolean FINISH_FAULT = Boolean.FALSE;

    /**
     * 申请/新建 生产任务单
     */
    @Transactional(rollbackFor = Throwable.class)
    public ProductionOrder create(ProductionOrder prodOrder) {
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "prodOrder");
        }
        if (prodOrder.getExpectedStartTime() == null || prodOrder.getExpectedFinishTime() == null) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "expectedStartTime、expectedFinishTime");
        }
        // 预投产时间必须小于等于完成时间
        if (prodOrder.getExpectedStartTime().after(prodOrder.getExpectedFinishTime())) {
            throw Throwables.runtimeException(RetCode.PROD_EXPECTED_TIME_INVALID);
        }
        // 明细不可空
        if (isEmpty(prodOrder.getItems())) {
            throw Throwables.runtimeException(RetCode.PROD_CREATE_ITEMS_EMPTY);
        }else {
            for (ProductionOrderItem item : prodOrder.getItems()) {
                //Material material = getProduceMaterial(item.getMacNo(), prodOrder.getBizType());
                int num = productableQuantity(prodOrder.getOrgId(), item.getMacNo(), prodOrder.getProduceType());
                if (num < item.getQuantity()) {
                    throw Throwables.runtimeException(RetCode.PROD_CHECK_STORE_ENOUGH);
                }
            }
        }
        ProductionOrder order = prodManager.create(prodOrder);
        return order;
    }

    /**
     * 获取投产物料信息
     */
    /*public Material getProduceMaterial(String macNo, ProductionBiz bizType) {
        if (isBlank(macNo) || bizType == null) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "macno、bizType");
        }
        Optional<Material> macOpt = basicDataInterfaceService.getMaterialByMacno(macNo);
        Material mac = macOpt.orElseThrow(() -> Throwables.runtimeException(RetCode.NOT_EXISTS_MATERIAL, macNo));
        switch (bizType) {
            // 整机业务：只能投成品（机头、套机）
            case WHOLE_MACHINE:
                if (!basicDataInterfaceService.isProduct(macNo)) {
                    throw Throwables.runtimeException(RetCode.PROD_PRODUCE_MAC_TYPE_NOT_MATCH);
                }
                break;
            // 主板业务：只能投主板
            case MAIN_BOARD:
                if (!basicDataInterfaceService.isMainBoard(macNo)) {
                    throw Throwables.runtimeException(RetCode.PROD_PRODUCE_MAC_TYPE_NOT_MATCH);
                }
                break;
            // 快速业务：都可以投
            case FAST: break;
            default: throw Throwables.runtimeException(RetCode.VALUE_INVALID, "bizType", bizType);
        }
        return mac;
    }*/

    /**
     * 生产任务单详情
     */
    //@MafactoryReadOnlyConnection
    public ProductionOrder detail(String id, boolean withItem, boolean withSn, boolean withLog) {
        return prodManager.queryById(id, withItem, withSn, withLog);
    }

    /**
     * 查该物料可投产的数量
     *
     * @param macNo 申请投产的物料
     */
    public int productableQuantity(String orgId, String macNo, ProductionType prodType) {
        /*List<InvStorageVo> invStorages = storageInterfaceService.getInvStorageList(orgId, singletonList(macNo), StorageType.WAIT_REPAIR, prodType.getSectionType());
        int storageQt = invStorages.stream().mapToInt(invStorage -> invStorage.getQuantity() - invStorage.getPreemptQuantity()).sum();
        if (storageQt < 1) {
            return 0;
        }
        // 查该机构投产中的物料数量
        int inProducingQt = prodManager.countInProducing(orgId, macNo, prodType);
        return storageQt - inProducingQt;*/
        return 2;
    }

    /**
     * 任务单列表
     *//*
    @MafactoryReadOnlyConnection
    public List<ProductionOrder> list(ProductionOrderQo qo) {
        PageVo page = qo.getPage();
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        // 机型查询 转换为 子物料集合
        if (qo.getThirdModelId() != null) {
            qo.setInMacNo(basicDataInterfaceService.getSubMacnoByModelId(qo.getOrgId(), qo.getThirdModelId()));
        }
        List<ProductionOrder> prodOrders = prodMapper.list(qo);
        // 填入 申请任务单 remark
        List<String> prodIds = prodOrders.stream().map(ProductionOrder::getId).collect(Collectors.toList());
        Map<String, String> remarkMap =  operateLogManager.mapRemark(OperateLogType.FACTORY_PROD_ORDER, OperateLogSubType.INSERT, prodIds);
        prodOrders.forEach(prodOrder -> prodOrder.setRemark(remarkMap.get(prodOrder.getId())));
        return prodOrders;
    }*/

    /**
     * 根据条件获取每种状态的单子数量
     */
    /*@MafactoryReadOnlyConnection
    public Map<ProductionOrderStatus, Long> mapStatusCount(ProductionOrderQo qo) {
        List<Map<String, Object>> counts = prodMapper.countStatus(qo);
        // key 状态，value 数量
        Map<ProductionOrderStatus, Long> statusMap = counts.stream()
                .collect(toMap(ele -> ProductionOrderStatus.getById(ele.get("status").toString()), ele -> (Long) ele.get("count")));
        // 要返回的集合
        Map<ProductionOrderStatus, Long> retMap = Stream.of(ProductionOrderStatus.values())
                .collect(toMap(identity(), status -> Optional.ofNullable(statusMap.get(status)).orElse(0L)));
        // 总数量
        retMap.put(ProductionOrderStatus.ALL, statusMap.values().stream().reduce(Long::sum).orElse(0L));
        return retMap;
    }*/

    /**
     * 审核通过
     */
    @Transactional(rollbackFor = Throwable.class)
    public void auditPass(String id, Long operator) {
        ProductionOrder prodOrder = prodManager.queryById(id);
        // 通用校验 todo 考虑封装
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (operator == null || operator == 0L) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "operator");
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_AUDIT) {
            throw Throwables.runtimeException(RetCode.PROD_STATUS_ACTION_NOT_MATCH, prodOrder.getStatus().getDescription(), "audit");
        }
        prodManager.updateStatus(id, ProductionOrderStatus.PENDING_AUDIT, ProductionOrderStatus.PENDING_PRODUCE);
        //prodManager.logOperate(id, OperateLogSubType.AUDIT, operator);
    }

    /**
     * 审核驳回
     */
    @Transactional(rollbackFor = Throwable.class)
    public void auditReject(String id, String remark, Long operator) {
        ProductionOrder prodOrder = prodManager.queryById(id);
        // 通用校验 todo 考虑封装
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (operator == null || operator == 0L) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "operator");
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_AUDIT) {
            throw Throwables.runtimeException(RetCode.PROD_STATUS_ACTION_NOT_MATCH, prodOrder.getStatus().getDescription(), "audit");
        }
        // 驳回业务校验
        if (isBlank(remark)) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "remark");
        }
        prodManager.updateStatus(id, ProductionOrderStatus.PENDING_AUDIT, ProductionOrderStatus.REJECTED);
        //prodManager.logOperate(id, OperateLogSubType.AUDIT, operator, ImmutableMap.of("remark", remark), remark);
    }

    /**
     * 投产异常取消
     */
    @Transactional(rollbackFor = Throwable.class)
    public void cancel(String id, String remark, Long operator) {
        ProductionOrder prodOrder = prodManager.queryById(id);
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (operator == null || operator == 0L) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "operator");
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_AUDIT && prodOrder.getStatus() != ProductionOrderStatus.PENDING_PRODUCE) {
            throw Throwables.runtimeException(RetCode.PROD_STATUS_ACTION_NOT_MATCH, prodOrder.getStatus().getDescription(), "cancel");
        }
        // 驳回业务校验
        if (isBlank(remark)) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "remark");
        }
        prodManager.updateStatus(id, prodOrder.getStatus(), ProductionOrderStatus.CANCELED);
        //prodManager.logOperate(id, OperateLogSubType.CANCEL, operator, ImmutableMap.of("remark", remark), remark);
    }

    /**
     * 校验投产 imei 是否正确
     * @return left：结果，right：结果对应的串号
     * left[1：成功, 2：无效串号，3：物料号不匹配，4：库区不匹配]
     * 成功时返回扫描正确的串号（会去重）
     */
    /*public CheckImeiResultVo checkProduceImeis(String id, String macNo, List<String> originImeis, boolean checkPhaseLevel) {
        ProductionOrder prodOrder = prodManager.queryById(id);
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_PRODUCE) {
            throw Throwables.runtimeException(RetCode.PROD_INVALID_STATUS_TO_SCAN_IMEI, prodOrder.getStatus().getDescription());
        }
        String orgId = prodOrder.getOrgId();
        // 扫描数量
        ProductionOrderItem item = prodManager.queryItem(id, macNo);
        if (item.getQuantity() < originImeis.size()) {
            throw Throwables.runtimeException(RetCode.PROD_SCAN_IMEI_OUT_OF_QT, originImeis.size(), item.getQuantity());
        }
        // 保持顺序，先去重
        List<String> imeis = originImeis.stream().distinct().collect(toList());
        List<InvImei> invImeis = storageInterfaceService.listInvImei(InvImei.builder().imeis(imeis).orgId(orgId).build());
        // 1、有无效串号
        if (invImeis.size() != imeis.size()) {
            Set<String> selectedImeisSet = invImeis.stream().map(InvImei::getImei1).collect(toSet());
            Map<Boolean, List<String>> partitionedImeis = imeis.stream().collect(Collectors.partitioningBy(imei -> !selectedImeisSet.contains(imei)));
            return CheckImeiResultVo.builder().result(CheckImeiResult.INVALID_IMEI).validImeis(partitionedImeis.get(Boolean.FALSE)).invalidImeis(partitionedImeis.get(Boolean.TRUE)).build();
        }
        // 2、物料号不匹配
        List<String> macNoUnmatchImeis = invImeis.stream().filter(imei -> !macNo.equals(imei.getMacno())).map(InvImei::getImei1).collect(toList());
        if (isNotEmpty(macNoUnmatchImeis)) {
            return CheckImeiResultVo.builder().result(CheckImeiResult.INVALID_IMEI)
                                    .validImeis(invImeis.stream().filter(imei -> macNo.equals(imei.getMacno()))
                                                        .map(InvImei::getImei1).collect(toList()))
                                    .invalidImeis(macNoUnmatchImeis)
                                    .build();
        }

        StorageSectionType sectionType = prodOrder.getProduceType().getSectionType();
        BaseStorageSection section = storageInterfaceService.findSection(orgId, StorageType.WAIT_REPAIR, sectionType);
        if (section == null) {
            throw Throwables.runtimeException(RetCode.STORAGE_SECTION_NOT_FOUND, orgId, StorageType.WAIT_REPAIR.getDesc(), sectionType.getDesc());
        }

        // 3、不在待维修库对应生产类型的区下 TODO:这个判断条件是为了解决超时回滚，实际已经出入库的场景，为了实现幂等，不是很严禁，如果出入库后输入的imei和第一次不通，会造成数据不一致．
        if (!storageInterfaceService.existServiceInOuted(id)) {
            List<String> sectionNotMatchImeis = invImeis.stream().filter(imei -> !section.getId().equals(String.valueOf(imei.getSectionId()))).map(InvImei::getImei1).collect(toList());
            if (isNotEmpty(sectionNotMatchImeis)) {
                return CheckImeiResultVo.builder().result(CheckImeiResult.SECTION_UNMATCH)
                        .validImeis(invImeis.stream().filter(imei -> section.getId().equals(String
                                .valueOf(imei.getSectionId()))).map(InvImei::getImei1).collect(toList()))
                        .invalidImeis(sectionNotMatchImeis)
                        .build();
            }
        }
        if (basicDataInterfaceService.isMainBoard(macNo)) {
            checkPhaseLevel = false;
        }
        // 4、品相优先级校验，当投产串号中有 C1、C2 品相时进行校验
        // 投 C1、C2 时库里不可以有更低的品相 栗子1: 投的带 C1 库里还有 C2,不通过 栗子2: 投的带 C1 库里没有 C2,通过 栗子3: 投的没 C1 有 C2，总通过
        // 抽象出来的规则就是 从投的串号中拿到品相最高的 品相（仅限 C1、C2）,再查库里是否还有比这个品相更低的串号，扩展性强，以后加入 C0 只要NEED_CHECK_PHASE_LEVEL 集合就行
        if (checkPhaseLevel) {
            Optional<PhaseLevel> highestLevelOpt = invImeis.stream().map(InvImei::getPhaseLevel).filter(ProductionOrderUtils::isNeedCheckPhaseLevel).distinct().min(comparing(PhaseLevel::getId));
            // 是否还有品相低的串号
            boolean hasLowerPhaseLevelImei = highestLevelOpt.map(highestLevel -> {
                List<PhaseLevel> lowerLevels = PhaseLevel.listLower(highestLevel).stream()
                                                         // 过滤掉不需要校验优先级的品相（比如 D）
                                                         .filter(ProductionOrderUtils::isNeedCheckPhaseLevel).collect(toList());
                // 没有比最高品相更低的
                if (isEmpty(lowerLevels)) {
                    return false;
                }
                return storageInterfaceService.hasMatchedInvImei(ImmutableMap.of("orgId", orgId, "sectionId", section.getId(), "macno", macNo, "inPhaseLevel", lowerLevels, "notInImeis", imeis));
            }).orElse(Boolean.FALSE);
            if (hasLowerPhaseLevelImei) {
                return CheckImeiResultVo.builder().result(CheckImeiResult.PHASE_LEVEL_UNMATCH).build();
            }
        }
        // 5、校验通过
        return CheckImeiResultVo.builder().result(CheckImeiResult.SUCESS).validImeis(imeis).build();
    }*/

    /**
     * 投产
     */
    //@Transactional(rollbackFor = Throwable.class)
    /*public void produce(ProductionOrder prodOrder, Long operator) throws Exception {
        String id = prodOrder.getId();
        ProductionBiz bizType = prodOrder.getBizType();
        Date createTime = new Date();
        List<ProductionOrderItem> items = prodOrder.getItems();
        prodOrder = prodManager.queryById(id);
        // 通用校验 todo 考虑封装
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (operator == null || operator == 0L) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "operator");
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_PRODUCE) {
            throw Throwables.runtimeException(RetCode.PROD_STATUS_ACTION_NOT_MATCH, prodOrder.getStatus().getDescription(), "produce");
        }

        // 校验实际投产数量
        items.forEach(item -> {
            if (item.getActualQuantity() > item.getQuantity() || item.getActualQuantity() < 0) {
                throw Throwables.runtimeException(RetCode.PROD_INVALID_QUANTITY, item.getMacNo(), item.getActualQuantity());
            }
        });
        int totalQt = items.stream().mapToInt(ProductionOrderItem::getActualQuantity).sum();
        if (totalQt <= 0) {
            throw Throwables.runtimeException(RetCode.PROD_INVALID_TOTAL_QUANTITY, totalQt);
        }
        items = items.stream().filter(item -> item.getActualQuantity() > 0).collect(toList());
        // 投产业务校验
        Optional<ProductionOrderItem> invalidItem = items.stream().filter(item -> item.getActualQuantity() == null).findAny();
        if (invalidItem.isPresent()) {
            throw Throwables.runtimeException(RetCode.PARAM_HAS_EMPTY, invalidItem.get().getMacName(), "actualQuantity");
        }
        // 串号管理的物料
        *//*List<ProductionOrderItem> serialItems = items.stream().filter(item -> item.getIsSerial() && isNotEmpty(item.getImeis()))
                                                     // 校验扫描的串号（库区、品相等等）
                                                     .peek(item -> {
                                                         CheckImeiResultVo resultVo = this.checkProduceImeis(id, item.getMacNo(), item.getImeis(), true);
                                                         if (resultVo.getResult() != CheckImeiResult.SUCESS) {
                                                             throw Throwables.runtimeException(RetCode.PROD_CHECK_PRODUCE_IMEIS_FAULT, resultVo.getResultDesc());
                                                         }
                                                     })
                                                     .collect(toList());
        // 实际投产的串号数量
        int actualProduceImeiQt = serialItems.stream().mapToInt(ProductionOrderItem::getActualQuantity).sum();
        List<String> produceImeis = serialItems.stream().map(ProductionOrderItem::getImeis).flatMap(Collection::stream).collect(toList());
        if (actualProduceImeiQt != produceImeis.size()) {
            throw Throwables.runtimeException(RetCode.PROD_IMEI_QT_NOT_MATCH, actualProduceImeiQt, produceImeis.size());
        }
        if (actualProduceImeiQt > 0) {
            // 串号详情，key imei
            Map<String, InvImei> imeiMap = storageInterfaceService.mapImeiByImeis(produceImeis);
            serialItems.forEach(item -> item.setItemSns(item.getImeis().stream()
                    .map(imeiMap::get)
                    .map(invImei -> {
                        ProductionOrderItemSn itemSn = invImeiToProdOrderItemSn(invImei);
                        itemSn.setCreateTime(createTime);
                        itemSn.setItemId(item.getId());
                        return itemSn;
                    }).collect(toList())));
            // 存投产串号信息
            prodManager.saveProduceImeis(serialItems.stream()
                    .map(ProductionOrderItem::getItemSns)
                    .flatMap(Collection::stream)
                    .collect(toList()));
        }*//*
        // 更新实际投产数量
        items.forEach(item -> prodManager.updateItemActualQt(id, item.getMacNo(), item.getActualQuantity()));
        prodManager.updateStatus(id, ProductionOrderStatus.PENDING_PRODUCE, ProductionOrderStatus.PENDING_FINISH);
        prodManager.logOperate(id, OperateLogSubType.PRODUCE, operator);
        // 创建修整单
        if (bizType == ProductionBiz.FAST) {
            this.produceForFastBiz(prodOrder, items, operator);
        } else {
            this.produceForNotFastBiz(prodOrder, items, operator);
        }
        // 转移库存到维修库
        //List<EasyInOutItemVo> inOutItems = items.stream().map(ProductionOrderUtils::prodOrderItemToInOutItem).collect(toList());
        //storageInterfaceService.inOutProduceOrder(prodOrder.getOrgId(), id, prodOrder.getProduceType(), operator, inOutItems);
    }*/

    /**
     * 投产：快速业务
     */
    /*private void produceForFastBiz(ProductionOrder prodOrder, List<ProductionOrderItem> items, Long operator) {
        // 生成修整单
        Repair repair = new Repair();
        repair.setProdId(prodOrder.getId());
        repair.setOrgId(prodOrder.getOrgId());
        repair.setCreatePerson(operator);
        repair.setServiceType(prodOrder.getBizType().getId());
        List<RepairItem> repairItems = items.stream()
                .map(item -> {
                    if (item.getIsSerial()) {
                        return RepairItem.builder()
                                .macno(item.getMacNo())
                                .assignNum(item.getActualQuantity())
                                // 串号信息
                                .sns(item.getItemSns().stream().map(ProductionOrderUtils::prodOrderItemSnToRepairItemSn).collect(toList()))
                                .build();
                    } else {
                        return RepairItem.builder().macno(item.getMacNo()).assignNum(item.getActualQuantity()).build();
                    }
                }).collect(toList());
        repair.setItems(repairItems);
        repairService.insertFastRepair(repair);
    }*/

    /**
     * 投产：整机/主板业务
     */
    /*private void produceForNotFastBiz(ProductionOrder prodOrder, List<ProductionOrderItem> items, Long operator) {
        List<Repair> repairs = items.stream()
                .flatMap(item -> item.getItemSns().stream().map(itemSn -> {
                    Repair repair = new Repair();
                    repair.setProdId(prodOrder.getId());
                    repair.setOrgId(prodOrder.getOrgId());
                    repair.setCreatePerson(operator);
                    repair.setServiceType(prodOrder.getBizType().getId());
                    repair.setImei(itemSn.getImei());
                    repair.setSn(itemSn.getSn());
                    repair.setMacno(item.getMacNo());
                    return repair;
                })).collect(toList());
        repairService.insertNoFastRepair(repairs);
    }*/

    /**
     * 完结
     * @return left：是否完结成功，right：不成功原因
     */
    //@Transactional(rollbackFor = Throwable.class)
    /*public Pair<Boolean, String> finish(String id, Long operator) {
        ProductionOrder prodOrder = prodManager.queryById(id, true);
        // 通用校验 todo 考虑封装
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PROD_NOT_FOUND, id);
        }
        if (operator == null || operator == 0L) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "operator");
        }
        if (prodOrder.getStatus() != ProductionOrderStatus.PENDING_FINISH) {
            throw Throwables.runtimeException(RetCode.PROD_STATUS_ACTION_NOT_MATCH, prodOrder.getStatus().getDescription(), "finish");
        }
        // 投产业务校验
        // 是否满足完结条件（所有名下的修整单都完结）
        *//*Optional<Repair> unfinishedRepair = repairManager.selectRepairs(ImmutableMap.of("ids", prodOrder.getRepairIds()), false).stream()
                .filter(repair -> !Objects.equals(repair.getStatus(), EnumRepairStatus.RECEIVED_END.getCode()))
                .findAny();

        // 不满足完结条件
        if (unfinishedRepair.isPresent()) {
            return ImmutablePair.of(FINISH_FAULT, "操作失败，请核查修整单状态");
        }*//*
        // 完结成功
        prodManager.updateStatus(id, ProductionOrderStatus.PENDING_FINISH, ProductionOrderStatus.FINISHED);
        prodManager.logOperate(id, OperateLogSubType.FINISH, operator);
        return ImmutablePair.of(FINISHED, "");
    }*/

}
