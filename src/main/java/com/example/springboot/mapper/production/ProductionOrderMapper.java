package com.example.springboot.mapper.production;

import com.example.springboot.module.production.ProductionOrder;
import com.example.springboot.module.production.ProductionOrderItem;
import com.example.springboot.module.production.ProductionOrderItemSn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 生产任务单
 * Created by sengzin on 2018/4/18.
 */
public interface ProductionOrderMapper {

    ProductionOrder select(Map param);

    List<ProductionOrderItem> listItem(Map param);

    List<ProductionOrderItemSn> listItemSn(Map param);

    /* order */

    @Insert("INSERT INTO t_srv_factory_prod_order(id, status, org_id, produce_type, biz_type, expected_start_time" +
            ", expected_finish_time, create_person, create_time) " +
            "VALUES(#{id}, #{status}, #{orgId}, #{produceType}, #{bizType}, #{expectedStartTime}" +
            ", #{expectedFinishTime}, #{createPerson}, #{createTime})")
    int insert(ProductionOrder prodOrder);
    @Update("UPDATE t_srv_factory_prod_order SET status = #{nextStatus} WHERE id = #{id} AND status = #{oldStatus}")
    int updateStatus(Map param);

    /*List<ProductionOrder> list(@Param("qo") ProductionOrderQo qo);

    List<Map<String, Object>> countStatus(@Param("qo") ProductionOrderQo qo);*/
    /* item */
    int insertItems(@Param("items") List<ProductionOrderItem> items);

    @Update("UPDATE t_srv_factory_prod_order_item SET actual_quantity = #{actualQt} WHERE prod_id = #{prodId} AND mac_no = #{macNo}")
    int updateItemActualQt(@Param("prodId") String prodId, @Param("macNo") String macNo, @Param("actualQt") int actualQt);

    int insertItemSns(@Param("itemSns") List<ProductionOrderItemSn> itemSns);
    // 查投产中的数量
    //Integer countInProducing(@Param("qo") ProductionOrderQo qo);

}
