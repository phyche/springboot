package com.example.springboot.designMode.factoryStrategy;

import com.example.springboot.designMode.factory.CashNormal;
import com.example.springboot.designMode.factory.CashRebate;
import com.example.springboot.designMode.factory.CashReturn;
import com.example.springboot.designMode.factory.CashSuper;

public class CashContext {

    private CashSuper cs = null;
    public CashContext(String type) {
        switch (type) {
            case "正常收费":
                cs = new CashNormal();
                break;
            case "满300-30":
                CashReturn cashReturn = new CashReturn("300","30");
                cs = cashReturn;
                break;
            case "打8折":
                CashRebate cashRebate = new CashRebate("0.8");
                cs = cashRebate;
                break;
        }
    }

    public double getResult(double money) {
        return cs.acceptCash(money);
    }
}
