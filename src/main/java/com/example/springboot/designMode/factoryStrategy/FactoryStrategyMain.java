package com.example.springboot.designMode.factoryStrategy;

import com.example.springboot.designMode.factory.CashFactory;
import com.example.springboot.designMode.factory.CashSuper;

/**
 * 抽象工厂模式和策略者模式结合
 */
public class FactoryStrategyMain {

    public static void main(String[] strg) {

        String price = "100";
        String num = "23";
        String type = "满300-30";
        CashContext cashContext = new CashContext(type);
        double totalPrice = cashContext.getResult(Double.valueOf(price) * Double.valueOf(num));
        System.out.println("总价格是：" + totalPrice);
    }
}
