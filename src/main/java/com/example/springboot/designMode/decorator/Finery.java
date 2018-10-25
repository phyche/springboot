package com.example.springboot.designMode.decorator;

/**
 * 服饰类（Decorator）
 */
public class Finery extends Person{

    protected Person component;

    public void decorate(Person component) {
        this.component = component;
    }

    @Override
    public void show() {
        if (component != null) {
            component.show();
        }
    }
}
