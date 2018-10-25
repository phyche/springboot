package com.example.springboot.designMode.factory;

/**
 * 简单工厂模式
 * 优点：
 * 工厂类中包含了必要的逻辑判断，根据客户端的选择动态实例化相关的类，对应客户端来说，去除了与具体产品的依赖
 *
 */
public class FactoryMain {

    public static void main(String[] strg) {

        String price = "100";
        String num = "23";
        String type = "满300-30";
        CashSuper cashSuper = CashFactory.createCashAccept(type);
        double totalPrice = cashSuper.acceptCash(Double.valueOf(price) * Double.valueOf(num));
        System.out.println("总价格是：" + totalPrice);
    }
}
