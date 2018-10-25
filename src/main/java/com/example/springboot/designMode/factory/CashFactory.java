package com.example.springboot.designMode.factory;

/**
 * 收费工厂类
 */
public class CashFactory {

    public static CashSuper createCashAccept(String type) {
        CashSuper cs = null;
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
        return cs;
    }
}
