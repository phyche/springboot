package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class TShirts extends Finery{

    @Override
    public void show() {
        System.out.println("大T恤");
        super.show();
    }
}
