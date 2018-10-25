package com.example.springboot.designMode.factoryMethod;

import com.example.springboot.designMode.factory.CashRebate;
import com.example.springboot.designMode.factory.CashSuper;

public class CashRebateFactory implements CashFactory {

    @Override
    public CashSuper createCash() {
        return new CashRebate("0.8");
    }
}
