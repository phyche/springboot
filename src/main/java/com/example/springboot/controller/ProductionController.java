package com.example.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.module.production.ProductionOrder;
import com.example.springboot.module.production.ProductionOrderItem;
import com.example.springboot.service.production.ProductionOrderService;
import com.example.springboot.util.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller()
@SpringBootApplication(scanBasePackages = {"com.example.springboot.service","com.example.springboot.mapper"})
@MapperScan("com.example.springboot.mapper")
public class ProductionController {

    @Autowired
    ProductionOrderService productionOrderService;

    @RequestMapping("/production")
    public ModelAndView production() {
        ModelAndView view = new ModelAndView();
        view.setViewName("production/production");
        return view;
    }

    @RequestMapping("/getProduceMaterial")
    public String getProduceMaterial(String body) {
        ProductionOrder productionOrder = new ProductionOrder();
        return JSONObject.toJSONString(productionOrder);
    }

    @RequestMapping("/listProdOrderBizType")
    @ResponseBody
    public String listProdOrderBizType(String params) {
        Map map = (Map) JSONObject.parse(params);
        String ids = (String) map.get("ids");
        ProductionOrder productionOrder = new ProductionOrder();
        return JSONObject.toJSONString(productionOrder);
    }

    @RequestMapping("/test")
    public String test() {
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setExpectedStartTime(DateUtil.getDate("2019-01-01 00:00:00","yyyy-MM-dd hh:mm:ss"));
        productionOrder.setExpectedFinishTime(new Date());
        List list = new ArrayList();
        ProductionOrderItem item = new ProductionOrderItem();
        item.setId(1l);
        item.setMacNo("1111111");
        item.setQuantity(1);
        list.add(item);
        productionOrder.setItems(list);
        productionOrder = productionOrderService.create(productionOrder);
        return JSONObject.toJSONString(productionOrder);
    }
}
