package com.example.springboot.designMode.command;

/**
 * 命令模式
 * 将请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或者记录请求日志，以及支持可撤销的操作
 *
 * 优点：
 * 1.较容易的设计一个命令队列
 * 2.在需要的情况下，可较容易的将命令记录进日志
 * 3.运行接收请求的一方决定是否要否决请求
 * 4.可以很容易的实现对请求的撤销和重做
 * 5.添加新的具体命令类很容易
 */
public class CommandMain {

    public static void main(String[] strg) {

        Barbecuer boy = new Barbecuer();
        Command muttonCommand = new BakeMuttonCommand(boy);
        Command muttonCommand2 = new BakeMuttonCommand(boy);
        Command chickenCommand = new BakeChickenWingCommand(boy);

        Waiter girl = new Waiter();
        girl.setOrder(muttonCommand);
        girl.setOrder(muttonCommand2);
        girl.setOrder(chickenCommand);

        girl.notifyAllExcute();

        girl.cancelOrder(muttonCommand2);
        girl.notifyAllExcute();
    }
}
