package com.example.springboot.designMode.chain;

/**
 * 职责链模式
 * 使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。将这个对象连成一条链，并沿着这条链传递
 * 该对象的请求，直到有一个对象处理它为止
 *
 * 优点：
 * 当客户提交一个请求时，请求是沿着链传递直至有一个ConcreteHandler对象负责处理它
 *
 */
public class ChainMain {

    public static void main(String[] strg) {

        CommonManager jingli = new CommonManager("经理");
        MajorManager zongjian = new MajorManager("总监");
        GeneralManager zongjingli = new GeneralManager("总经理");
        jingli.setSuperior(zongjian);
        zongjian.setSuperior(zongjingli);

        Request request = new Request();
        request.setRequestType("请假");
        request.setRequestContent("小菜请假");
        request.setRequestNum(1);
        jingli.requestApplication(request);

        request.setRequestNum(4);
        jingli.requestApplication(request);

        request.setRequestType("加薪");
        request.setRequestContent("小菜请求加薪");
        request.setRequestNum(500);
        jingli.requestApplication(request);

        request.setRequestNum(1000);
        jingli.requestApplication(request);

    }
}
