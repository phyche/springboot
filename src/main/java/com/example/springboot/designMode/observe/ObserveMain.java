package com.example.springboot.designMode.observe;

/**
 * 观察者模式（发布-订阅模式） 解除耦合
 * 定义了一种一对多的依赖关系，让多个观察者对象同时监听主题对象。这个主题对象在状态发生变化时会通知所有观察者对象，使他们能够自动更新自己
 *
 * 使用场合：
 * 当一个对象的改变需要同时改变其他对象时，而且不知道具体有多少对象待改变
 */
public class ObserveMain {

    public static void main(String[] strg) {
        Secretary secretary = new Secretary();
        secretary.setSubjectState("老板回来了!");

        StockObserve stockObserve = new StockObserve("张三",secretary);
        NBAObserve nbaObserve = new NBAObserve("李四",secretary);

        secretary.attach(stockObserve);
        secretary.attach(nbaObserve);

        secretary.detach(stockObserve);//实际上张三没有被通知到，所以移除

        secretary.notifyObserve();
    }
}
