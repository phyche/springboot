package com.example.springboot.designMode.decorator;

/**
 * 具体服饰类（ConcreteDecorate）
 */
public class Tie extends Finery{

    @Override
    public void show() {
        System.out.println("领带");
        super.show();
    }
}
