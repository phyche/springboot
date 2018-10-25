package com.example.springboot.designMode.mediator;

public class USA extends Country {

    public USA(UnitedNations mediator) {
        super(mediator);
    }

    @Override
    public void declare(String message) {
        mediator.declare(message,this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("美国获得对方消息：" + message);
    }
}
