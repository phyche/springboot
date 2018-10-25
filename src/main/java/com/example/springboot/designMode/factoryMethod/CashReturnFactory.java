package com.example.springboot.designMode.factoryMethod;

import com.example.springboot.designMode.factory.CashReturn;
import com.example.springboot.designMode.factory.CashSuper;

public class CashReturnFactory implements CashFactory {

    @Override
    public CashSuper createCash() {
        return new CashReturn("300","30");
    }
}
