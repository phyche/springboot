package com.example.springboot.designMode.mediator;

public abstract class Country {

    protected UnitedNations mediator;
    public Country(UnitedNations mediator) {
        this.mediator = mediator;
    }

    public abstract void declare(String message);
    public abstract void getMessage(String message);
}
