package com.example.springboot.util;


import com.example.springboot.module.production.RetCode;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 异常工具类
 * create by lichengzhen in 18-4-24
 */
public class Throwables {
    private Throwables() {}

    /**
     * 创建受检异常
     * @param rc         返回码
     * @param i18nParams i18n 参数
     */
    public static MafactoryException exception(RetCode rc, Object... i18nParams) {
        return exception(null, rc, i18nParams);
    }

    public static MafactoryException exception(Throwable cause, RetCode rc, Object... i18nParams) {
        return new MafactoryException(cause, rc.getId(), rc.getDescriptionWithParam(i18nParams));
    }

    /**
     * 创建运行时异常
     */
    public static MafactoryRuntimeException runtimeException(RetCode rc, Object... i18nParams) {
        return runtimeException(null, rc, i18nParams);
    }

    public static MafactoryRuntimeException runtimeException(Throwable cause, RetCode rc, Object... i18nParams) {
        return new MafactoryRuntimeException(cause, rc.getId(), rc.getDescriptionWithParam(i18nParams));
    }

    /*
    其他需要的再补充
    如 apiException(); 等
     */

    /**
     * 转为有用的信息
     * 背景：像 NullPointerException 是没有 message 的，传给前端的信息非常有限
     * 所以如果没有 message 则返回异常名跟报错的第一个位置，提高排查效率
     */
    public static String toUsefulMessage(Throwable throwable) {
        return throwable.getClass().getName() + ":" + (isNotBlank(throwable.getMessage()) ? throwable.getMessage() : "\n" + throwable.getStackTrace()[0].toString());
    }

}
