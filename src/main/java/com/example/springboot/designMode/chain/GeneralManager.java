package com.example.springboot.designMode.chain;

public class GeneralManager extends Manager {

    public GeneralManager(String name) {
        super(name);
    }

    @Override
    public void requestApplication(Request request) {

        if (request.getRequestType().equals("请假")) {
            System.out.println(name + ":" + request.getRequestType() + "  数量" + request.getRequestNum() + "  被批准");
        } else if (request.getRequestType().equals("加薪") && request.getRequestNum() <= 500){
            System.out.println(name + ":" + request.getRequestType() + "  数量" + request.getRequestNum() + "  被批准");
        } else if (request.getRequestType().equals("加薪") && request.getRequestNum() > 500){
            System.out.println(name + ":" + request.getRequestType() + "  数量" + request.getRequestNum() + "  再说吧");
        }
    }
}
