package com.example.springboot.module.production;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum StorageSectionType implements MafEnum {
    DEFAULT((byte) 0, "def", "普通区"),
    DELIVER_IN((byte) 1, "dne", "待检收货区"),
    DELIVER_OUT((byte) 2, "doe", "待发货区"),
    BORROW_AREA((byte) 3, "bas", "借用区"),
    OBLIGATION_SECTION((byte) 4, "obs", "待付款区"),
    PREPARE_REPAIR_SECTION((byte) 5, "prs", "待修整区"),
    ALREADY_REPAIR_SECTION((byte) 6, "ars", "已修整区"),
    UNABLE_REPAIR_SECTION((byte) 7, "urs", "无法修整区"),
    PREPARE_REPAIRT_GOOD_SECTION((byte) 8, "prgs", "待维修区"),
    WORKING_REPAIR_SECTION((byte) 9, "wrs", "维修区"),
    HIGH_WORTH_SECTION((byte) 10, "hwe", "高值区"),
    REPAIR_FAILED_SECTION((byte)11,"rfs","未修好待返区"),
    // 待鉴定区 (报废用)
    TO_APPRAISE_SECTION((byte)12,"tas","待鉴定区"),
    APPRAISE_AUDIT_SECTION((byte)13,"aas","鉴定区"),
    RETURN_TOSCRAP_SECTION((byte)14,"rts","已鉴定待报废已返还区"),
    RETURN_CANREPAIR_SECTION((byte)15,"rcs","已鉴定可修复已返还区"),
    BUY_OUT_RETURN((byte)16,"bor","买断返还区"),
    GOOD_THING_SECTION((byte)17,"gts","良官区"),
    MAINBOARD_TO_MACHINE_SECTION((byte)18,"btms","主板组套机区"),
    AB_MACHINE_SECTION((byte)19,"abms","AB套机区"),
    C_MACHINE_SECTION((byte)20,"cms","C套机区"),
    AB_BARE_MACHINE_SECTION((byte)21,"abbms","AB机头区"),
    C_BARE_MACHINE_SECTION((byte)22,"cbms","C机头区"),
    DEFECT_MACHINE_SECTION((byte)23,"dms","瑕疵机区"),
    MAINBOARD_SECTION((byte)24,"mbs","主板区"),
    FAST_REPAIR_SECTION((byte)25,"frs","快速区"),
    REPAIR_PRODUCE_SECTION((byte)26,"rps","维修投产区"),
    EXCEPTION_SECTION((byte)27,"ects","异常区"),
    WAIT_CHECK_SECTION((byte)28,"wcs","待质检区"),
    SCRAP_DISASSEMBLE_SECTION((byte)29,"sds","报废拆机区"),
    WAIT_IDENTIFY_SCRAP_SECTION((byte)30,"wiss","待鉴定报废区"),
    SPECIAL_SECTION((byte)31,"spcs","特殊区");

    private static final Map<String, StorageSectionType> codeMap = new HashMap<String, StorageSectionType>();

    static {
        for (StorageSectionType s : EnumSet.allOf(StorageSectionType.class)) {
            codeMap.put(s.code, s);
        }
    }

    private byte id;
    private String code;
    private String desc;

    StorageSectionType(byte id, String code, String desc) {
        this.id = id;
        this.code = code;
        this.desc = desc;
    }

    public static StorageSectionType get(String code) {
        return codeMap.get(code);
    }

    public String getCode() {
        return code;
    }

    @Override
    public byte getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
