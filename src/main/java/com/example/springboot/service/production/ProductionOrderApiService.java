/*
package com.example.springboot.service.production;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.mi.xms.basicdata.material.dto.Material;
import com.mi.xms.mafactory.biz.dto.ProductionOrderQo;
import com.mi.xms.mafactory.biz.entity.ProductionOrder;
import com.mi.xms.mafactory.biz.util.ProductionOrderUtils;
import com.mi.xms.mafactory.core.enums.ProductionBiz;
import com.mi.xms.mafactory.core.enums.ProductionOrderStatus;
import com.mi.xms.mafactory.core.enums.ProductionType;
import com.mi.xms.mafactory.core.enums.RetCode;
import com.mi.xms.mafactory.core.util.JsonUtil;
import com.mi.xms.mafactory.core.util.Throwables;
import com.mi.xms.mafactory.service.ProductionOrderService;
import com.mi.xms.mafactory.web.annotation.Api;
import com.mi.xms.mafactory.web.annotation.ApiService;
import com.mi.xms.mafactory.web.util.ApiUtil;
import com.mi.xms.mafactory.web.util.VoUtils;
import com.mi.xms.mafactory.web.vo.SelectOptionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mi.xms.mafactory.core.util.JsonUtil.getBoolean;
import static com.mi.xms.mafactory.core.util.JsonUtil.getText;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

*/
/**
 * @author lichengzhen
 * @Description 生产任务单
 * @Date 18-4-24
 *//*


@Slf4j
@ApiService
public class ProductionOrderApiService {

    @Autowired
    private ProductionOrderService service;

    */
/**
     * 获取生产单业务类型/生产类型下拉选项
     *//*

    @Api
    public List<SelectOptionVo> listProdOrderBizType() {
        return Stream.of(ProductionBiz.values()).map(prodBiz -> {
            SelectOptionVo prodBizVo = VoUtils.toSelectOptionVo(prodBiz);
            prodBizVo.setChilds(ProductionType.listByProdBizId(prodBizVo.getValue()).stream().map(VoUtils::toSelectOptionVo).collect(Collectors.toList()));
            return prodBizVo;
        }).collect(Collectors.toList());
    }

    */
/**
     * 查询投产物料信息
     *//*

    @Api
    public Material getProduceMaterial(String bodyStr) throws Exception {
         JsonNode bodyJson = JsonUtil.readTree(bodyStr);
        String macNo = getText(bodyJson, "macNo");
        ProductionBiz bizType = ProductionBiz.getById(getText(bodyJson, "bizType"));
        return service.getProduceMaterial(macNo, bizType);
    }

    */
/**
     * 查询某物料可投产的数量
     *//*

    @Api
    public Map<String, Object> productableQuantity(String bodyStr) throws Exception {
        JsonNode bodyJson = JsonUtil.readTree(bodyStr);
        String macNo = getText(bodyJson, "macNo");
        String orgId = getText(bodyJson, "orgId");
        ProductionType prodType = ProductionType.getById(getText(bodyJson, "prodType"));
        int quantity = service.productableQuantity(orgId, macNo, prodType);
        return ImmutableMap.of("quantity", quantity);
    }

    */
/**
     * 申请/新建 生产任务单
     *//*

    @Api
    public Map<String, Object> apply(String bodyStr) throws Exception {
        ProductionOrder prodOrder = JsonUtil.parse(bodyStr, ProductionOrder.class);
        for (ProductionOrderItem item : prodOrder)
        prodOrder.setCreatePerson(ApiUtil.getX5Operator());
        prodOrder = service.create(prodOrder);
        return ImmutableMap.of("id", prodOrder.getId());
    }

    */
/**
     * 任务单列表
     *//*

    @Api
    public Object list(String bodyStr) throws Exception {
        ProductionOrderQo qo = JsonUtil.parse(bodyStr, ProductionOrderQo.class);
        List<ProductionOrder> items = service.list(qo);
        // 每个 tab 下单子的数量
        qo.setStatus(null);
        Map<ProductionOrderStatus, Long> statusCountMap = service.mapStatusCount(qo);
        return ImmutableMap.of("items", items, "page", ApiUtil.getPageVoByList(items), "statusQt", statusCountMap);
    }

    */
/**
     * 生产任务单详情
     *//*

    @Api
    public ProductionOrder detail(String bodyStr) throws Exception {
        JsonNode bodyJson = JsonUtil.readTree(bodyStr);
        String id = getText(bodyJson, "id");
        boolean withSn = getBoolean(bodyJson, "withSn").orElse(false);
        boolean withItem = getBoolean(bodyJson, "withItem").orElse(false);
        boolean withLog = getBoolean(bodyJson, "withLog").orElse(false);
        ProductionOrder prodOrder = service.detail(id, withItem, withSn, withLog);
        return prodOrder;
    }

    */
/**
     * 审核通过
     *//*

    @Api
    public Map<String, Object> auditPass(String bodyStr) throws Exception {
        JsonNode bodyJson = JsonUtil.readTree(bodyStr);
        String id = getText(bodyJson, "id");
        Long operator = ApiUtil.getX5Operator();
        service.auditPass(id, operator);
        return ImmutableMap.of("id", id);
    }

    */
/**
     * 审核驳回
     *//*

    @Api
    public Map<String, Object> auditReject(String bodyStr) throws Exception {
        JsonNode bodyJson = JsonUtil.readTree(bodyStr);
        String id = getText(bodyJson, "id");
        String remark = getText(bodyJson, "remark");
        Long operator = ApiUtil.getX5Operator();
        service.auditReject(id, remark, operator);
        return ImmutableMap.of("id", id);
    }

    */
/**
     * 校验投产 IMEI
     *//*

    @Api
    public Object checkProduceImei(String bodyStr) throws Exception {
        JsonNode bodyJson = JsonUtil.getObjectMapper().readTree(bodyStr);
        String id = getText(bodyJson, "id");
        String macNo = getText(bodyJson, "macNo");
        List<String> imeis = ProductionOrderUtils.getImeisFromReqJson(bodyJson);
        if (isEmpty(imeis)) {
            return ImmutableMap.of("allMatched", true, "imeis", emptyList());
        }
        if (isBlank(id) || isBlank(macNo)) {
            throw Throwables.runtimeException(RetCode.PARAM_MUST_NOT_EMPTY, "macNo、imeisStr");
        }
        return service.checkProduceImeis(id, macNo, imeis, false);
    }

    */
/**
     * 投产
     *//*

    @Api
    public Map<String, Object> produce(String bodyStr) throws Exception {
        ProductionOrder prodOrder = JsonUtil.parse(bodyStr, ProductionOrder.class);
        Long operator = ApiUtil.getX5Operator();
        service.produce(prodOrder, operator);
        return ImmutableMap.of("id", prodOrder.getId());
    }

    */
/**
     * 完结
     *//*

    @Api
    public Map<String, Object> finish(String bodyStr) throws Exception {
        ProductionOrder prodOrder = JsonUtil.parse(bodyStr, ProductionOrder.class);
        Long operator = ApiUtil.getX5Operator();
        Pair<Boolean, String> result = service.finish(prodOrder.getId(), operator);
        return ImmutableMap.of("finished", result.getLeft(), "message", result.getRight());
    }

}
*/
