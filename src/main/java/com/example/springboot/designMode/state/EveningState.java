package com.example.springboot.designMode.state;

public class EveningState extends State {

    @Override
    public void writeProgram(Work work) {

        if (work.isFinish()) {
            work.setState(new RestState());
        } else {
            if (work.getHour() < 21) {
                System.out.println("当前时间：" + work.getHour() + "点  加班哦，疲惫至极。");
            } else {
                work.setState(new SleepingState());
                work.writeProgram();
            }
        }

    }
}
