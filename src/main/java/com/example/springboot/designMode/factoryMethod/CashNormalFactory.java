package com.example.springboot.designMode.factoryMethod;

import com.example.springboot.designMode.factory.CashNormal;
import com.example.springboot.designMode.factory.CashSuper;

public class CashNormalFactory  implements CashFactory{

    @Override
    public CashSuper createCash() {
        return new CashNormal();
    }
}
