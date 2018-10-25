package com.example.springboot.designMode.observe;

public class StockObserve extends Observe {

    public StockObserve(String name, Subject subject) {
        super(name, subject);
    }

    @Override
    public void update() {
        System.out.println(name+ "," + subject.getSubjectState()+ "关闭股票行情，继续工作");
    }
}
