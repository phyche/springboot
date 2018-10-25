package com.example.springboot.designMode.singleton;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction;

import java.util.concurrent.locks.Lock;

/**
 * 懒汉模式（加锁）
 */
public class Singleton2 {

    private static Singleton2 instance;
    private static final Object syncRoot = new Object();
    private Singleton2() {

    }

    public static Singleton2 getInstance() {

        synchronized (syncRoot) {
            if (instance == null) {
                instance = new Singleton2();
            }
        }
        return instance;
    }
}
