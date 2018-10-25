package com.example.springboot.designMode.state;

public class Work {

    private State current;
    public Work() {
        current = new ForenoonState();
    }

    private Double hour;

    public Double getHour() {
        return hour;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }

    private boolean finish = false;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void setState(State state) {
        current = state;
    }

    public void writeProgram() {
        current.writeProgram(this);
    }
}
