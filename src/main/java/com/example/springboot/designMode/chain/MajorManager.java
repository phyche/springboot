package com.example.springboot.designMode.chain;

public class MajorManager extends Manager {

    public MajorManager(String name) {
        super(name);
    }

    @Override
    public void requestApplication(Request request) {

        if (request.getRequestType().equals("请假") && request.getRequestNum() <= 5) {
            System.out.println(name + ":" + request.getRequestType() + "  数量" + request.getRequestNum() + "  被批准");
        } else {
            if (superior != null) {
                superior.requestApplication(request);
            }
        }
    }
}
