package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class BigTrousers extends Finery{

    @Override
    public void show() {
        System.out.println("垮裤");
        super.show();
    }
}
