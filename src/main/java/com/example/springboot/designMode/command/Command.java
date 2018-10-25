package com.example.springboot.designMode.command;

public abstract class Command {

    protected Barbecuer receiver;

    public Command(Barbecuer barbecuer) {
        this.receiver = barbecuer;
    }

    public abstract void excuteCommand();
    public abstract String commandContent();
}
