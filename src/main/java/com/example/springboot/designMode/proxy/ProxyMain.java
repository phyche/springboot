package com.example.springboot.designMode.proxy;

/**
 * 代理模式
 * 为其他对象提供一种代理以控制对这个对象的访问
 *
 * 使用场合
 * 1.远程代理，也就是为一个对象在不同的地址空间提供局部代表。这样可以隐藏一个对象存在于不同地址空间的事实
 * 2.虚拟代理，根据需要创建开销很大的对象。通过它来存放实例化需要很长时间的对象
 * 3.安全代理，用来控制真实对象的访问权限
 * 4.智能指引，是指调用真实的对象时，代理处理另外一些事
 */
public class ProxyMain {

    public static void main(String[] strg) {

        SchoolGirl schoolGirl = new SchoolGirl();
        schoolGirl.setName("李娇娇");

        Proxy proxy = new Proxy(schoolGirl);
        proxy.giveDolls();
        proxy.giveFlowers();
        proxy.giveChocolate();
    }
}
