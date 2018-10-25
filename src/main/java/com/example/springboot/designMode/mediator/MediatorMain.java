package com.example.springboot.designMode.mediator;

/**
 * 中介者模式
 * 用一个中介对象来封装一系列的对象交互。中介者使各对象不需要显示的相互引用，从而使其耦合松散，而且可以独立的改变之间的交互
 *
 * 优点：
 * 1.Mediator的出现减少了各个Colleague的耦合，使得可以独立的改变和复用各个Colleague和Mediator
 * 2.由于把对象如何协作做了抽象，将中介作为一个独立的概念并将其封装在一个对象中，这样关注的对象就从各对象本身的行为转移到他们
 * 之间的交互上来，也就是站在一个更宏观的角度去看待系统
 * 缺点：
 * 由于ConcreteMediator控制了集中化，于是就把交互复杂性变为了中介者的复杂性，这就使得中介者比任何一个ConcreteMediator都复杂
 *
 * 应用场合：
 * 应用于一组对象以定义良好但是复杂的方式进行通信的场合
 */
public class MediatorMain {

    public static void main(String[] strg) {

        UnitedNationsSecurityCouncil UNSC = new UnitedNationsSecurityCouncil();

        USA c1 = new USA(UNSC);
        Iraq c2 = new Iraq(UNSC);

        UNSC.setColleague1(c1);
        UNSC.setColleague2(c2);

        c1.declare("不准研制核武器，否则要发动战争！");
        c1.declare("我们没有核武器，也不怕侵略！");
    }
}
