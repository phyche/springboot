package com.example.springboot.designMode.chain;

public class CommonManager extends Manager {

    public CommonManager(String name) {
        super(name);
    }

    @Override
    public void requestApplication(Request request) {

        if (request.getRequestType().equals("请假") && request.getRequestNum() <= 2) {
            System.out.println(name + ":" + request.getRequestType() + "  数量" + request.getRequestNum() + "  被批准");
        } else {
            if (superior != null) {
                superior.requestApplication(request);
            }
        }
    }
}
