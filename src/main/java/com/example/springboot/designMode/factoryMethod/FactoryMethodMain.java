package com.example.springboot.designMode.factoryMethod;

import com.example.springboot.designMode.factory.CashSuper;

/**
 * 工厂方法模式
 * 定义了一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类
 *
 * 工厂方法实现时，客户端需要决定实例化哪一个工厂来实现运算类，选择判断的问题还是存在，也就是说，工厂方法把简单工厂的内部逻辑
 * 判断移到了客户端端代码来进行。想要加功能，本来是改工厂类的，而现在是修改客户端。
 */
public class FactoryMethodMain {

    public static void main(String[] strg) {
        String price = "100";
        String num = "23";
        CashFactory cashFactory = new CashReturnFactory();//创建返利工厂
        CashSuper cashSuper = cashFactory.createCash();//执行返利工厂的方法
        double totalPrice = cashSuper.acceptCash(Double.valueOf(price) * Double.valueOf(num));
        System.out.println("总价格是：" + totalPrice);
    }
}
