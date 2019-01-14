package com.example.springboot.module.production;

import com.example.springboot.util.RepairUtils;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;


/**
 * 生产任务单统计信息
 * Qt：quantity 缩写
 * create by lichengzhen in 18-5-26
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderStatistics implements Serializable {
    private static final long serialVersionUID = -7385770823125693268L;
    // 良品返还 Qt：quantity 缩写
    private int goodReturnQt;
    // 坏品返还
    private int badReturnQt;
    // 已维修数量
    private int repairedQt;
    // 报废/无法维修数量
    private int scrapQt;
    // 不正常/异常数量
    private int abnormalQt;

    /**
     * 总结非快速业务修整单
     */
    public static ProductionOrderStatistics summarizingNonFastRepair(List<Repair> repairs) {
        ProductionOrderStatistics stat = new ProductionOrderStatistics();
        if (isEmpty(repairs)) return stat;
        repairs.forEach(repair -> {
            int srvType = repair.getServiceType();
            if (srvType == ProductionBiz.FAST.getId()) {
                repair.getItems().forEach(item -> {
                    stat.goodReturnQt += item.getGoodReturnNum();
                    stat.badReturnQt += item.getBadReturnNum();
                });
            } else {
                String status = repair.getStatus();
                if (RepairUtils.canTurnOutExceptionArea(status)) {
                    ++stat.abnormalQt;
                } else if (EnumReturnPoolType.REPAIRED.getId().equals(repair.getReturnPoolType())) {
                    ++stat.repairedQt;
                } else if (EnumReturnPoolType.NOT_REPAIRD.getId().equals(repair.getReturnPoolType())) {
                    ++stat.scrapQt;
                }
            }
        });
        return stat;
    }

    /**
     * 总结快速业务修整单
     */
    public static ProductionOrderStatistics summarizingFastRepair(List<RepairItem> repairItems) {
        ProductionOrderStatistics stat = new ProductionOrderStatistics();
        if (isEmpty(repairItems)) return stat;
        repairItems.forEach(item -> {
            stat.goodReturnQt += item.getGoodReturnNum();
            stat.badReturnQt += item.getBadReturnNum();
        });
        return stat;
    }

    /**
     * 总数
     */
    public int sum() {
        return goodReturnQt + badReturnQt + repairedQt + scrapQt + abnormalQt;
    }

    /**
     * 完成的数量
     */
    public int finishedQt() {
        return goodReturnQt + badReturnQt + repairedQt + scrapQt;
    }
}
