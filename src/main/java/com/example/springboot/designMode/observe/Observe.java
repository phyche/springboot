package com.example.springboot.designMode.observe;

public abstract class Observe {

    protected String name;
    protected Subject subject;

    public Observe(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public abstract void update() ;
}
