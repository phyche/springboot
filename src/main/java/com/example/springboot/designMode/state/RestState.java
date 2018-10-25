package com.example.springboot.designMode.state;

public class RestState extends State {

    @Override
    public void writeProgram(Work work) {
        System.out.println("当前时间："+ work.getHour() + "点下班回家了。");
    }
}
