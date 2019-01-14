package com.example.springboot.util;

/**
 * 不提供 code、msg 为空的构造方法
 * @see com.mi.xms.mafactory.core.util.Throwables 尽量用工具类创建
 * create by lichengzhen in 18-4-24
 */
public class MafactoryRuntimeException extends RuntimeException {
    private int code;

    public MafactoryRuntimeException(int code, String msgTemplate, Object... args) {
        super(String.format(msgTemplate, args));
        this.code = code;
    }

    public MafactoryRuntimeException(Throwable cause, int code, String msgTemplate, Object... args) {
        super(String.format(msgTemplate, args), cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
