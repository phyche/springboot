package com.example.springboot.designMode.chain;

public abstract class Manager {

    protected String name;
    protected Manager superior;//上级

    public Manager(String name) {
        this.name = name;
    }

    public Manager getSuperior() {
        return superior;
    }

    public void setSuperior(Manager superior) {
        this.superior = superior;
    }

    public abstract void requestApplication(Request request);
}
