package com.example.springboot.mapper.production;

import com.example.springboot.module.production.Repair;
import com.example.springboot.module.production.RepairItem;
import com.example.springboot.module.production.RepairItemSn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * @author cylion
 * @Description
 * @Date Created in 2018/5/2 15:22
 */
public interface RepairMapper {
    /* 修整单 */
    List<Repair> selectRepairs(@Param("conditions") Map conditions);

    List<String> listRepairIds(@Param("conditions") Map conditions);

    List<Map> countRepairs(@Param("conditions") Map conditions);

    int insertRepairs(@Param("items") List<Repair> items);

    int updateRepair(@Param("model") Repair model);

    int updateRepairBatch(@Param("model") Map model);

    List<RepairItem> selectItems(@Param("conditions") Map conditions);

    int insertItems(@Param("list") List<RepairItem> items);

    int updateItem(@Param("model") RepairItem model);

    /* 修整单串号 */
    List<RepairItemSn> selectSns(@Param("conditions") Map conditions);

    int insertSns(@Param("items") List<RepairItemSn> items);

    int updateSn(@Param("model") RepairItemSn model);

    /* 修整单用料 *//*
    List<RepairMac> selectRepairMacs(@Param("conditions") Map conditions);

    int insertRepairMacs(List<RepairMac> items);

    int insertRepairMacSns(List<RepairMacSn> itemSns);

    List<RepairMacSn> selectRepairMacSns(@Param("conditions") Map conditions);

    int updateRepairMac(@Param("model") RepairMac model);*/

    /***
     * applyNo不能为null或者空字符串,如果有这个在外层要拦截掉,否则会导致t_srv_factory_repair_mac表的历史数据applyNo为空字符串的数据被删除.
     * @param applyNo
     * @return
     */
    int deleteByApplyNo(@Param("applyNo") String applyNo);

    /* 屏幕dsn绑定 *//*
    int insertSnUploads(@Param("items") List<RepairSnUpload> items);

    int updateSnUploads(@Param("model") RepairSnUpload model);

    List<RepairSnUpload> findSnUploadBySn(@Param("sns") List<String> sns);
    List<RepairSnUpload> findSnUploadByOldSn(@Param("oldSns") List<String> oldSns);
    List<RepairSnUpload> findSnUploadByRepairId(String repairId);*/

}
