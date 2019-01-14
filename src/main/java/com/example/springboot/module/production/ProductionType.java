package com.example.springboot.module.production;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.springboot.module.production.ProductionBiz.FAST;
import static com.example.springboot.module.production.ProductionBiz.MAIN_BOARD;
import static com.example.springboot.module.production.ProductionBiz.WHOLE_MACHINE;

import static java.util.function.Function.identity;

/**
 * 投产类型
 * 数据库定义类型为 tinyint，故 id 不可超过 255
 * Created by lichengzhen in 2018/4/14.
 */
public enum ProductionType {
    // 整机业务
    GOOD_TURNING(11, "良官", WHOLE_MACHINE, StorageSectionType.GOOD_THING_SECTION),
    MAIN_BOARD_ASSEMB_SET(12, "主板组套机", WHOLE_MACHINE, StorageSectionType.MAINBOARD_TO_MACHINE_SECTION),
    AB_SET(13, "AB 套机", WHOLE_MACHINE, StorageSectionType.AB_MACHINE_SECTION),
    C_SET(14, "C 套机", WHOLE_MACHINE, StorageSectionType.C_MACHINE_SECTION),
    AB_HEAD(15, "AB 机头", WHOLE_MACHINE, StorageSectionType.AB_BARE_MACHINE_SECTION),
    C_HEAD(16, "C 机头", WHOLE_MACHINE, StorageSectionType.C_BARE_MACHINE_SECTION),
    DEFECTIVE(17, "瑕疵机", WHOLE_MACHINE, StorageSectionType.DEFECT_MACHINE_SECTION),
    // 主板业务
    MAIN_BOARD_REPAIR(31, "主板维修", MAIN_BOARD, StorageSectionType.MAINBOARD_SECTION),
    // 快速业务
    PLASTIC_PACKAGE(51, "塑封", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    DISASSEMBLE(52, "拆机", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    PICK_GOOD(53, "分拣良品", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    PICK_BAD(54, "分拣不良品", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    CLEAR_MAIN_BOARD_BOX_SET(55, "清洁主板盒（套装）", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    CLEAR_MAIN_BOARD_BOX_SINGLE(56, "单独清洁主板盒", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    CLEAR_FOAM_SINGLE(57, "单独清洁泡棉", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    SUB_BOARD_REPAIR(58, "手机副板修整", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    SOFTWARE_UPGRADE(59, "软件升级", FAST, StorageSectionType.FAST_REPAIR_SECTION),
    WORK_AGAIN(60, "重工", FAST, StorageSectionType.FAST_REPAIR_SECTION) // 重工？什么鬼？
    ;
    private Integer id;
    private String description;
    // 所属业务类型
    private ProductionBiz prodBiz;
    // 投产物品所在的库区
    private StorageSectionType sectionType;

    private static final Map<String, ProductionType> INDEX_ID = Stream.of(ProductionType.values()).collect(Collectors.toMap(e -> e.getId().toString(), identity()));
    private static final Map<ProductionBiz, List<ProductionType>> INDEX_PROD_BIZ = Stream.of(ProductionType.values()).collect(Collectors.groupingBy(ProductionType::getProdBiz));

    /**
     * 根据 投产业务类型 id 获取 子枚举
     */
    public static List<ProductionType> listByProdBizId(Integer prodBizId) {
        return listByProdBiz(ProductionBiz.getById(prodBizId));
    }

    /**
     * 根据 投产业务类型 id 获取 子枚举
     */
    public static List<ProductionType> listByProdBizId(String prodBizId) {
        return listByProdBiz(ProductionBiz.getById(prodBizId));
    }

    /**
     * 通过投产业务类型获取子类型
     */
    public static List<ProductionType> listByProdBiz(ProductionBiz prodBiz) {
        return prodBiz != null ? INDEX_PROD_BIZ.get(prodBiz) : Collections.EMPTY_LIST;
    }

    /**
     * 根据 id 获取
     */
    public static ProductionType getById(String id) {
        return INDEX_ID.get(id);
    }

    // 序列化
    @JsonValue
    public Integer getId() {
        return this.id;
    }

    // 反序列化
    @JsonCreator
    public static ProductionType getById(Integer id) {
        return getById(String.valueOf(id));
    }

    ProductionType(Integer id, String description, ProductionBiz prodBiz, StorageSectionType sectionType) {
        this.id = id;
        this.description = description;
        this.prodBiz = prodBiz;
        this.sectionType = sectionType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductionBiz getProdBiz() {
        return prodBiz;
    }

    public void setProdBiz(ProductionBiz prodBiz) {
        this.prodBiz = prodBiz;
    }

    public StorageSectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(StorageSectionType sectionType) {
        this.sectionType = sectionType;
    }

    public static Map<String, ProductionType> getIndexId() {
        return INDEX_ID;
    }

    public static Map<ProductionBiz, List<ProductionType>> getIndexProdBiz() {
        return INDEX_PROD_BIZ;
    }
}
