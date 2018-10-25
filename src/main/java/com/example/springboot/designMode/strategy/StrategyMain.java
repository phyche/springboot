package com.example.springboot.designMode.strategy;

import com.example.springboot.designMode.factory.CashNormal;
import com.example.springboot.designMode.factory.CashRebate;
import com.example.springboot.designMode.factory.CashReturn;

/**
 * 策略模式
 * 它定义了算法家族，分别封装起来，让他们之间可以互相替换，此模式让算法的变化，不会影响到使用算法的客户
 *
 * 优点：简化了单元测试
 */
public class StrategyMain {

    public static void main(String[] strg) {

        String type = "满300-30";
        CashContext cs = null;
        switch (type) {
            case "正常收费":
                cs = new CashContext(new CashNormal());
                break;
            case "满300-30":
                cs = new CashContext(new CashReturn("300","30"));
                break;
            case "打8折":
                cs = new CashContext(new CashRebate("0.8"));
                break;
        }

        String price = "100";
        String num = "23";
        double totalPrice = cs.getResult(Double.valueOf(price) * Double.valueOf(num));
        System.out.println("总价格是：" + totalPrice);
    }
}
