package com.example.springboot.designMode.observe;

public class NBAObserve extends Observe {

    public NBAObserve(String name, Subject subject) {
        super(name, subject);
    }

    @Override
    public void update() {
        System.out.println(name+ "," + subject.getSubjectState()+ "关闭NBA球赛，继续工作");
    }
}
