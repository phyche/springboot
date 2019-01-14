package com.example.springboot.module.production;

import lombok.AllArgsConstructor;

/**
 * 返回码枚举
 *
 * 全系统返回码大全
 *   http://wiki.n.miui.com/pages/viewpage.action?pageId=75702991
 *
 * 售后返回码大全
 *   http://wiki.n.miui.com/pages/viewpage.action?pageId=68202440
 *
 * mafactory watch log 范围
 *   27000-27999
 *
 * 系统占用
 *   小于20000
 * create by lichengzhen in 18-4-25
 */
public enum RetCode  {
    /*------------------ 系统级 --------------------*/

    SUCCESS(200, "success"),
    FORBIDDEN_SIGN_INCRERRET(403, "forbidden.signIncerret"),
    FORBIDDEN_APPID_INVALID(403, "forbidden.appidInvalid"),
    FORBIDDEN_OPERATOR_EMPTY(403, "forbidden.operatorEmpty"),
    NOT_EXISTS_METHOD(404, "notFound.method"),
    METHOD_NOT_ALLOWED(405, "methodNotAllowed"),
    BAD_REQUEST(400, "badRequest"),
    UNPROCESSABLE_ENTITY(422, "unprocessableEntity"),
    INTERNAL_SERVER_ERROR(500, "internalServerError"),

    /*------------------ 业务级 --------------------*/

    /* 27000 - 27099 比较通用的 code，todo 上线前 code 可以随意改，上线后建议少改动已定义的 */
    PARAM_MUST_NOT_EMPTY(27000, "param.mustNotNull"),
    DB_WRITE_FAULT(27001, "dbWriteFault"),
    NOT_EXISTS_MATERIAL(27002, "notFound.material"),
    INVALID_QO_TIME_TYPE(27003, "invalid.qo.timeType"),
    DB_AFFECT_ROWS_NOT_MATCH(27004, "db.affectRowsNotMatch"),
    PARAM_HAS_EMPTY(27005, "param.hasEmpty"),
    STORAGE_SECTION_NOT_FOUND(27005, "storage.sectionNotFound"),
    QUERY_REPORT_MUST_HAVE_DURING(27006, "query.reportMustHaveDuring"),
    QUERY_REPORT_MUST_DURING_ONE_MONTH(27007, "query.reportMustDuringOneMonth"),
    IMEIPOOL_NOT_FOUND(27008, "imeiPool.notFound"),
    VALUE_INVALID(27009, "value.invalid"),
    INSERT_FAIL(27010, "insert.fail"),
    BILL_NO_REPEART(27011, "bill.no.repeart"),
    XMS_STORAGE_SERVICE_UNAVAILABLE(27012, "xms.storage.service.unavailable"),
    XMS_INV_IMEI_NOT_FOUND(27013, "xms.inv.imei.not.found"),


    /* 27100 - 27199 生产任务单 code */
    PROD_CREATE_ITEMS_EMPTY(27100, "prod.create.itemsEmpty"),
    PROD_NOT_FOUND(27101, "prod.notFound"),
    PROD_STATUS_ACTION_NOT_MATCH(27102, "prod.statusActionNotMatch"),
    PROD_IMEI_QT_NOT_MATCH(27103, "prod.imeiQtNotMatch"),
    PROD_INVALID_STATUS_TO_SCAN_IMEI(27104, "prod.invalidStatusToScanImei"),
    PROD_SCAN_IMEI_OUT_OF_QT(27105, "prod.scanImeiOutOfQt"),
    PROD_WHOLE_MACHINE_BIZ_NEED_3_MODEL(27106, "prod.wholeMachineBizNeed3Model"),
    PROD_PRODUCE_MAC_TYPE_NOT_MATCH(27107, "prod.produceMacTypeNotMatch"),
    PROD_EXPECTED_TIME_INVALID(27108, "prod.expectedTimeInvalid"),
    PROD_INVALID_QUANTITY(27109, "prod.invalidQuantity"),
    PROD_INVALID_TOTAL_QUANTITY(27110, "prod.invalidTotalQuantity"),
    PROD_CHECK_PRODUCE_IMEIS_FAULT(27111, "prod.checkProduceImeisFault"),
    PROD_CHECK_STORE_ENOUGH(27112, "prod.checkStoreisNotEnough"),

    /* 27200 - 27299 维修单 code */
    REPAIR_FAST_CREATE_ILLEGAL(27200, "repair.fast.create.illegal"),
    REPAIR_NOFAST_CREATE_ILLEGAL(27201, "repair.nofast.create.illegal"),
    REPAIR_FAST_UPDATE_ILLEGAL(27202, "repair.fast.update.illegal"),
    REPAIR_STATUS_CHANGE_ILLEGAL(27203, "repair.status.change.illegal"),
    REPAIR_STATUS_OPER_NOTMATCH(27204, "repair.status.oper.notmatch"),
    REPAIR_HANDLE_USEMAC_SNSIZE_ILLEGAL(27205, "repair.handle.usemac.snsize.illegal"),
    REPAIR_NOT_FOUND(27206, "repair.notFound"),
    REPAIR_UPDATE_ILLEGAL(27207, "repair.update.illegal"),
    REPAIR_BATCH_QUERY_IMEIS_TOOMANY(27208, "repair.batch.query.imeis.toomany"),
    REPAIR_BATCH_UPDATE_FORBIDDEN_FAST(27209, "repair.batch.update.forbidden.fast"),
    REPAIR_BATCH_UPDATE_STATUS_ILLEGAL(27210, "repair.batch.update.status.illegal"),
    REPAIR_BATCH_UPDATE_CHECK_INVALID_ID(27211, "repair.batch.update.check.invalid.id"),
    REPAIR_FAST_MACAPPLY_MUST_FAST(27212, "repari.fast.macapply.must.fast"),
    REPAIR_FAST_MACAPPLY_STATUS_ILLEGAL(27213, "repari.fast.macapply.status.illegal"),
    REPAIR_MACAPPLY_INPRICE_NOFOUND(27214, "inprice.notfound"),
    REPAIR_MACAPPLY_REPEAT(27215, "repair.macapply.repeat"),
    REPAIR_MACRETURN_QUANTITY_ERROR(27216, "repair.macreturn.quantity.error"),
    REPAIR_FINAL_CHECK_INVALID_IMEI(27217,"repair.final.check.invalid.imei"),
    REPAIR_FINAL_CHECK_INVALID_FSN(27218, "repair.final.check.invalid.fsn"),
    REPAIR_USEMAC_HIGHVALUE_QUANTITY_INVALID(27219, "repair.usemac.highvalue.quantity.invalid"),
    REPAIR_RECEIVE_BATCH_TOOMANY(27220, "repair.receive.batch.toomany"),
    REPAIR_USE_MAC_REPLACED_MAC_NOTNULL(27221, "repair.use.mac.replaced.mac.notnull"),
    REPAIR_USE_MAC_QUANTITY_NOZERO(27222, "repair.use.mac.quantity.nozero"),
    REPAIR_USE_MAC_STASH_MUST_USE_MAC(27223, "repair.use.mac.stash.must.use.mac"),
    REPAIR_EXIST_INOUT_EXCEPTION(27224, "repair.exist.inout.exception"),
    REPAIR_PENGDING_INOUTING(27225, "repair.pengding.inouting"),
    REPAIR_INOUT_NOTEXIST(27226, "repair.inout.notexist"),
    REPAIR_PENDING_INOUTED_NOCANCEL(27227, "repair.pending.inouted.nocancel"),
    REPAIR_USEMAC_OLD_IMEI_NOTNULL(27228, "repair.usemac.old.imei.notnull"),
    REPAIR_USEMAC_NEW_IMEI_NOTNULL(27229, "repair.usemac.new.imei.notnull"),
    REPAIR_USEMAC_NEW_IMEI_NOT_IN_PERSONAL_MGS(27230, "repair.usemac.new.imei.not.in.personal.mgs"),
    REPAIR_USEMAC_NEW_IMEI_NOT_MATCH_MACNO(27231, "repair.usemac.new.imei.not.match.macno"),
    REPAIR_USEMAC_NEW_IMEI_STATUS_INVALID(27232, "repair.usemac.new.imei.status.invalid"),
    REPAIR_USE_MAC_SERIAL_MSG_NO_SN(27233, "repair.use.mac.serial.msg.no.sn"),
    REPAIR_USE_MAC_NOT_MATCH_IMEI_SERVICE(27234, "repair.use.mac.not.match.imei.service"),
    REPAIR_DSN_UPLOAD_MAC_INVALID(27235, "repair.dsn.upload.mac.invalid"),
    REPAIR_DSN_UPLOAD_QUANTITY_NOT_MATCH(27236, "repair.dsn.upload.quantity.not.match"),
    REPAIR_DSN_UPLOAD_MUST_FAST_REPAIR(27237, "repair.dsn.upload.must.fast.repair"),
    REPAIR_DSN_UPLOAD_MUST_RECEIVED(27238, "repair.dsn.upload.must.received"),
    REPAIR_DSN_UPLOAD_FAILED(27239, "repair.dsn.upload.failed"),

    /* 27300 - 27399 网标 code */
    NETWORKSTANDARD_INSERT_FAIL(27300, "networkstandard.insert.fail"),
    NETWORKSTANDARD_MODEL_NOT_FOUND(27301, "networkstandard.model.notFound"),
    NETWORKSTANDARD_MATERIAL_NOT_FOUND(27302, "networkstandard.material.notFound"),
    NETWORKSTANDARD_LACK_STOCK(27303, "networkstandard.lack.stock"),
    NETWORKSTANDARD_BRAND_CLASS_NOT_FOUND(27304, "networkstandard.brand.class.notFound"),
    NETWORKSTANDARD_BRAND_CLASS_NOT_MATCH(27305, "networkstandard.brand.class.notMatch"),

    ;
    // 返回码
    private int id;

    private String i18nKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    RetCode(int id, String i18nKey) {
        this.id = id;
        this.i18nKey = i18nKey;
    }

    /**
     * 带参的获取描述方法
     */
    public String getDescriptionWithParam(Object... args) {
        return null;
    }
}
