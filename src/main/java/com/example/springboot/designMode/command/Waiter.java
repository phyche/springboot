package com.example.springboot.designMode.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Waiter {

    private List<Command> orderList = new ArrayList<>();

    public void setOrder(Command command) {

        orderList.add(command);
        System.out.println("增加订单：" + command.commandContent());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("  时间：" + df.format(new Date()));
    }

    public void cancelOrder(Command command){
        orderList.remove(command);
        System.out.println("取消订单：" + command.commandContent());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("  时间：" + df.format(new Date()));
    }

    public void notifyAllExcute() {

        for (Command command : orderList) {
            command.excuteCommand();
        }
    }
}
