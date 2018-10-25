package com.example.springboot.designMode.visitor;

public class Failing extends Action {

    public Failing(String action) {
        super(action);
    }

    @Override
    public void getMenConclusion(Men men) {
        System.out.println(men.name + this.action + "时，闷头喝酒，谁也不用劝。");
    }

    @Override
    public void getWomenConclusion(Women women) {
        System.out.println(women.name + this.action + "时，眼泪汪汪，谁也劝不了。");
    }
}
