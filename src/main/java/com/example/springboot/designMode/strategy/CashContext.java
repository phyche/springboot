package com.example.springboot.designMode.strategy;

import com.example.springboot.designMode.factory.CashSuper;

public class CashContext {

    private CashSuper cs;
    public CashContext(CashSuper cashSuper) {
        this.cs = cashSuper;
    }

    public double getResult(double money) {
        return cs.acceptCash(money);
    }
}
