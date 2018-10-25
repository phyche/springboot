package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class Suit extends Finery{

    @Override
    public void show() {
        System.out.println("西装");
        super.show();
    }
}
