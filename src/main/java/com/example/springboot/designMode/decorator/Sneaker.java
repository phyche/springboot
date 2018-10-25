package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class Sneaker extends Finery{

    @Override
    public void show() {
        System.out.println("破球鞋");
        super.show();
    }
}
