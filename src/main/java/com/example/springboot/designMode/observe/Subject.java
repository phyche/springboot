package com.example.springboot.designMode.observe;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    protected String subjectState;//被观察者状态

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }

    //增加观察者
    public abstract void attach(Observe observe);

    //移除观察者
    public abstract void detach(Observe observe);

    //通知观察者
    public abstract void notifyObserve();
}
