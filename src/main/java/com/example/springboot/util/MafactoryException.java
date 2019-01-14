package com.example.springboot.util;

/**
 * 不提供 code、msg 为空的构造方法
 *
 * create by lichengzhen in 18-4-24
 */
public class MafactoryException extends Exception {
    private int code;

    public MafactoryException(int code, String msgTemplate, Object... args) {
        super(String.format(msgTemplate, args));
        this.code = code;
    }

    public MafactoryException(Throwable cause, int code, String msgTemplate, Object... args) {
        super(String.format(msgTemplate, args), cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
