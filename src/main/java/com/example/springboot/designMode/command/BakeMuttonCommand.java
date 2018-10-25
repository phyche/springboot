package com.example.springboot.designMode.command;

public class BakeMuttonCommand extends Command {

    public BakeMuttonCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }

    @Override
    public void excuteCommand() {
        receiver.bakeButton();
    }

    @Override
    public String commandContent() {
        String content = "命令模式.烤羊肉串";
        return content;
    }
}
