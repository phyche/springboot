package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class LeatherShoes extends Finery{

    @Override
    public void show() {
        System.out.println("皮鞋");
        super.show();
    }
}
