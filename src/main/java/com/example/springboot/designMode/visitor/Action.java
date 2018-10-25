package com.example.springboot.designMode.visitor;

/**
 * visitor类
 */
public abstract class Action {

    protected String action;

    public Action(String action) {
        this.action = action;
    }

    public abstract void getMenConclusion(Men men);
    public abstract void getWomenConclusion(Women women);
}
