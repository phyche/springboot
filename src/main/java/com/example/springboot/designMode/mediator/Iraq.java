package com.example.springboot.designMode.mediator;

public class Iraq extends Country {

    public Iraq(UnitedNations mediator) {
        super(mediator);
    }

    @Override
    public void declare(String message) {
        mediator.declare(message,this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("伊拉克获得对方消息：" + message);
    }
}
