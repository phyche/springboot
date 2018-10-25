package com.example.springboot.designMode.visitor;

public class Women extends Person {

    public Women(String name) {
        super(name);
    }

    @Override
    public void accept(Action action) {
        action.getWomenConclusion(this);
    }
}
