package com.example.springboot.designMode.visitor;

public class Amativeness extends Action {

    public Amativeness(String action) {
        super(action);
    }

    @Override
    public void getMenConclusion(Men men) {
        System.out.println(men.name + this.action + "时，不懂也要装懂。");
    }

    @Override
    public void getWomenConclusion(Women women) {
        System.out.println(women.name + this.action + "时，懂也要装不懂。");
    }
}
