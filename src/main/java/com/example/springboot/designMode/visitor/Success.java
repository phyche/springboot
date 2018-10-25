package com.example.springboot.designMode.visitor;

public class Success extends Action {

    public Success(String action) {
        super(action);
    }

    @Override
    public void getMenConclusion(Men men) {
        System.out.println(men.name + this.action + "时，背后多半有一个伟大的女人。");
    }

    @Override
    public void getWomenConclusion(Women women) {
        System.out.print(women.name + this.action + "时，背后多半有一个不成功的男人。");
    }
}
