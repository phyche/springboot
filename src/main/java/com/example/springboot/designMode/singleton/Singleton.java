package com.example.springboot.designMode.singleton;

/**
 * 懒汉模式
 */
public class Singleton {

    private static Singleton instance;
    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
