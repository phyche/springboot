package com.example.springboot.designMode.factory;

/**
 * 返利收费子类
 */
public class CashReturn extends CashSuper{

    private double moneyCondition = 0.0;
    private double moneyReturn = 0.0;
    public CashReturn(String moneyCondition, String moneyReturn){
        this.moneyCondition = Double.valueOf(moneyCondition);
        this.moneyReturn = Double.valueOf(moneyReturn);
    }

    @Override
    public double acceptCash(double money) {
        double result = money;
        if (money > moneyCondition) {
            result  = money - Math.floor(money/moneyCondition) * moneyReturn;
        }
        return result;
    }
}
