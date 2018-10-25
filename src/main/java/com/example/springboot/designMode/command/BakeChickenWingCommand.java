package com.example.springboot.designMode.command;

public class BakeChickenWingCommand extends Command {

    public BakeChickenWingCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }

    @Override
    public void excuteCommand() {
        receiver.bakeChickenWing();
    }

    @Override
    public String commandContent() {
        String content = "命令模式.烤鸡翅";
        return content;
    }
}
