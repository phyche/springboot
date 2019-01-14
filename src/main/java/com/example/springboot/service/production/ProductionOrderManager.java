package com.example.springboot.service.production;
import com.example.springboot.mapper.production.ProductionOrderMapper;
import com.example.springboot.module.production.*;
import com.example.springboot.util.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author lichengzhen
 * @Description 产任务单 管理层
 * @Date 2018/4/14.
 */


@Slf4j
@Service
public class ProductionOrderManager {
    @Autowired
    private ProductionOrderMapper productionOrderMapper;
    /*@Autowired
    private BillNoManager billNoManager;
    @Autowired
    private BasicDataInterfaceService basicDataInterfaceService;
    @Autowired
    private OperateLogManager operateLogManager;
    @Autowired
    private SrvRepairManager repairManager;*/

    private static final String PRODORDER = "prodOrder";

    /**
     * 创建 任务单
     */
    public ProductionOrder create(ProductionOrder prodOrder) {
        if (prodOrder == null) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, PRODORDER);
        }
        //　由于XZPO前缀占用４位，一般来讲2位前缀保留１５位，所以4位前缀保留１７位
//        String id = billNoManager.getBillno(IdType.PRODUCTION_ORDER, 17);
        String id = "111111111";
        prodOrder.setId(id);
        Date createTime = new Date();
        // 1、存任务单信息
        prodOrder.setStatus(ProductionOrderStatus.PENDING_AUDIT);
        prodOrder.setCreateTime(createTime);
        if (productionOrderMapper.insert(prodOrder) < 1) {
            throw Throwables.runtimeException(RetCode.DB_WRITE_FAULT);
        }
        // 2、存操作记录
        //operateLogManager.log(ProductionOrderUtils.genOperateLog(prodOrder, OperateLogSubType.INSERT));
        // 3、存明细
        List<ProductionOrderItem> items = prodOrder.getItems();
        items.forEach(item -> {
            item.setProdId(id);
            item.setCreateTime(createTime);
        });
        int insertItemsResult = productionOrderMapper.insertItems(items);
        if (insertItemsResult != items.size()) {
            //log.error("存生产任务单明细失败，数目不符。插入[{}] 返回[{}] 单号[{}], 操作人[{}]。", items.size(), insertItemsResult, id, prodOrder.getId());
            throw Throwables.runtimeException(RetCode.DB_AFFECT_ROWS_NOT_MATCH, items.size(), insertItemsResult);
        }
        return prodOrder;
    }

    /**
     * 保存投产的串号信息
     */
    public void saveProduceImeis(List<ProductionOrderItemSn> itemSns) {
        int affectRows = productionOrderMapper.insertItemSns(itemSns);
        if (affectRows != itemSns.size()) {
            throw Throwables.runtimeException(RetCode.DB_AFFECT_ROWS_NOT_MATCH, 1 , affectRows);
        }
    }

    /**
     * 查询生产任务单
     */
    public ProductionOrder queryById(String id) {
        Map map = new HashMap();
        map.put("id",id);
        ProductionOrder prodOrder = productionOrderMapper.select(map);
        return prodOrder;
    }

    public ProductionOrder queryById(String id, boolean withItem) {
        return this.queryById(id, withItem, false, true);
    }

    public ProductionOrder queryById(String id, boolean withItem, boolean withSn, boolean withLog) {
        Map map = new HashMap();
        map.put("id",id);
        ProductionOrder prodOrder = productionOrderMapper.select(map);
        /*if (prodOrder != null) {
            // 查操作记录列表
            *//*if (withLog) {
                prodOrder.setOperateLogs(operateLogManager.mapOperateLog(OperateLogType.FACTORY_PROD_ORDER, id));
            }*//*
            List<String> repairIds = repairManager.listRepairIdsByProdId(id);
            prodOrder.setRepairIds(repairIds);
            // 投产明细
            if (withItem) {
                prodOrder.setItems(this.queryItemsById(id, withSn));
            }
        }*/
        return prodOrder;
    }

    /*public Map<String, ProductionOrder> mapByIds(List<String> prodIds) {
        return productionOrderMapper.list(ProductionOrderQo.builder().inProdOrderId(prodIds).build()).stream().collect(toMap(ProductionOrder::getId, identity()));
    }*/

    public ProductionOrderItem queryItem(String id, String macNo) {
        Map map = new HashMap();
        map.put("prodId",id);
        map.put("macNo",macNo);
        List<ProductionOrderItem> items = productionOrderMapper.listItem(map);
        return items != null ? items.get(0) : null;
    }

    /**
     * 查询生产任务单明细
     */
    /*private List<ProductionOrderItem> queryItemsById(String id, boolean withSn) {
        List<ProductionOrderItem> items = productionOrderMapper.listItem(ImmutableMap.of("prodId", id));
        // 任务单下的修整单
        List<Repair> repairs = repairManager.selectRepairs(ImmutableMap.of("prodId", id), false);
        // 修整单，key 物料号
        Map<String, List<Repair>> repairMap = repairs.stream().collect(groupingBy(Repair::getMacno));
        // 修整单明细，key 物料号
        Map<String, List<RepairItem>> repairItemMap = repairManager.selectItems(StreamUtils.distinct(repairs, Repair::getId)).stream().collect(groupingBy(RepairItem::getMacno));
        // 物料信息，key 物料号
        Map<String, Material> macMap = basicDataInterfaceService.mapMaterialByMacNos(items.stream().map(ProductionOrderItem::getMacNo).collect(toList()));
        // 机型名称，key 机型 id
        items.forEach(item -> {
            String macno = item.getMacNo();
            Material mac = macMap.get(item.getMacNo());
            item.setIsSerial(mac != null && mac.getIsSerial() == YesNoEnum.YES);
            item.setMacName(mac != null ? mac.getMacname() : "");
            if (isNotEmpty(repairMap.get(macno))) {
                item.setStatistics(ProductionOrderStatistics.summarizingNonFastRepair(repairMap.get(macno)));
            } else {
                item.setStatistics(ProductionOrderStatistics.summarizingFastRepair(repairItemMap.get(macno)));
            }
        });
        if (withSn) {
            this.fillItemSns(items);
        }
        return items;
    }*/

    /**
     * 给明细填充串号明细
     * @param items id 字段必须有
     */
    private List<ProductionOrderItem> fillItemSns(List<ProductionOrderItem> items) {
        Map map = new HashMap();
        map.put("itemIds",items.stream().map(ProductionOrderItem::getId).collect(toList()));
        List<ProductionOrderItemSn> itemSns = productionOrderMapper.listItemSn(map);
        Map<Long, List<ProductionOrderItemSn>> itemSnMap = itemSns.stream().collect(Collectors.groupingBy(ProductionOrderItemSn::getItemId));
        items.forEach(item -> item.setItemSns(itemSnMap.get(item.getId())));
        return items;
    }

    /**
     * 查物料在投产中的数量
     */
    /*public int countInProducing(String orgId, String macNo, ProductionType prodType) {
        ProductionBiz prodBiz = prodType.getProdBiz();
        ProductionOrderQo qo = ProductionOrderQo.builder()
                .orgId(orgId)
                .macNo(macNo)
                .bizType(prodType.getProdBiz())
                .inStatus(Arrays.asList(ProductionOrderStatus.PENDING_AUDIT, ProductionOrderStatus.PENDING_PRODUCE))
                .build();
        // 快速业务共用一个库区所以查业务类型下的投产中数量
        if (prodBiz != ProductionBiz.FAST) {
            qo.setProduceType(prodType);
        }
        return Optional.ofNullable(productionOrderMapper.countInProducing(qo)).orElse(0);
    }*/

    /**
     * 更新实际投产数量
     */
    public void updateItemActualQt(String prodId, String macNo, int actualQt) {
        int affectRows = productionOrderMapper.updateItemActualQt(prodId, macNo, actualQt);
        if (affectRows != 1) {
        throw Throwables.runtimeException(RetCode.DB_AFFECT_ROWS_NOT_MATCH, 1 , affectRows);
    }
}

    /**
     * 更新状态
     */
    public void updateStatus(String id, ProductionOrderStatus oldStatus, ProductionOrderStatus nextStatus) {
        Map map = new HashMap();
        map.put("id",id);
        map.put("oldStatus",oldStatus);
        map.put("nextStatus",nextStatus);
        int affectRows = productionOrderMapper.updateStatus(map);
        if (affectRows < 1) {
            throw Throwables.runtimeException(RetCode.DB_AFFECT_ROWS_NOT_MATCH, 1 , affectRows);
        }
    }

    /**
     * 记录操作信息
     * @param prodOrderId 任务单号
     * @param subType     操作类型
     * @param operator    操作人
     * @param content     内容
     */
   /* public void logOperate(String prodOrderId, OperateLogSubType subType, Long operator, Object content, String remark) {
        operateLogManager.log(OperateLog.builder()
                .type(OperateLogType.FACTORY_PROD_ORDER)
                .subType(subType)
                .operateKey(prodOrderId)
                .content(JsonUtil.writeValueQuite(content))
                .remark(remark)
                .createPerson(operator)
                .createTime(new Date()).build());
    }*/

    /*public void logOperate(String prodOrderId, OperateLogSubType subType, Long operator) {
        this.logOperate(prodOrderId, subType, operator, null, "");
    }*/

}
