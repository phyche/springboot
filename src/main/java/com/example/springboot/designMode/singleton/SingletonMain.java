package com.example.springboot.designMode.singleton;

public class SingletonMain {

    public static void main(String[] strg) {

        test1();
        test2();
        test3();
        test4();

    }

    public static void test1() {

        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        if (s1 == s2) {
            System.out.println("两个对象是相同的实例");
        }
    }

    public static void test2() {

        Singleton2 s1 = Singleton2.getInstance();
        Singleton2 s2 = Singleton2.getInstance();

        if (s1 == s2) {
            System.out.println("两个对象是相同的实例");
        }
    }

    public static void test3() {

        Singleton3 s1 = Singleton3.getInstance();
        Singleton3 s2 = Singleton3.getInstance();

        if (s1 == s2) {
            System.out.println("两个对象是相同的实例");
        }
    }

    public static void test4() {

        Singleton4 s1 = Singleton4.getInstance();
        Singleton4 s2 = Singleton4.getInstance();

        if (s1 == s2) {
            System.out.println("两个对象是相同的实例");
        }
    }
}
