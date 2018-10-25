package com.example.springboot.designMode.visitor;

/**
 * element类
 */
public abstract class Person {

    protected String name;

    public Person(String name) {
        this.name = name;
    }
    public abstract void accept(Action action);
}
