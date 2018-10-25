package com.example.springboot.designMode.singleton;

/**
 * 懒汉模式（双重锁定）
 */
public class Singleton3 {

    private static Singleton3 instance;
    private static final Object syncRoot = new Object();
    private Singleton3() {

    }

    public static Singleton3 getInstance() {

        if(instance == null) {
            synchronized (syncRoot) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
