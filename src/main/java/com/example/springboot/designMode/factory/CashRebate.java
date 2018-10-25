package com.example.springboot.designMode.factory;

/**
 * 打折收费子类
 */
public class CashRebate extends CashSuper{

    private double rebate = 1.0;
    public CashRebate(String rebate) {
        this.rebate = Double.valueOf(rebate);
    }

    @Override
    public double acceptCash(double money) {
        return rebate * money;
    }
}
