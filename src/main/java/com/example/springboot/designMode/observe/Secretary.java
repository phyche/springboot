package com.example.springboot.designMode.observe;

import java.util.ArrayList;
import java.util.List;

public class Secretary extends Subject {

    protected String action;
    protected List<Observe> observes = new ArrayList<>();

    public String getSubjectState() {
        return action;
    }

    public void setSubjectState(String subjectState) {
        this.action = subjectState;
    }

    @Override
    public void attach(Observe observe) {
        observes.add(observe);
    }

    @Override
    public void detach(Observe observe) {
        observes.remove(observe);
    }

    @Override
    public void notifyObserve() {
        for (Observe observe : observes) {
            observe.update();
        }
    }
}
