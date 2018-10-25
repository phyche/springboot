package com.example.springboot.designMode.visitor;

public class Men extends Person {

    public Men(String name) {
        super(name);
    }

    @Override
    public void accept(Action action) {
        action.getMenConclusion(this);
    }
}
