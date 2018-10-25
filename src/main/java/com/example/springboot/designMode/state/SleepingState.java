package com.example.springboot.designMode.state;

public class SleepingState extends State {

    @Override
    public void writeProgram(Work work) {
        System.out.println("当前时间：" + work.getHour() + "点不行了，睡着了。");
    }
}
