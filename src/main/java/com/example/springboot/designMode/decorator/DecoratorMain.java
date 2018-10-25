package com.example.springboot.designMode.decorator;

/**
 * 装饰模式
 * 动态给一个对象添加一些额外的职责，就增加功能来说，装饰模式比生成子类更加灵活
 *利用setComponent来对对象进行包装的
 *
 * 优点：把类中的装饰功能从类中搬移去除，这样可以简化原有的类，好处就是有效的把类的核心职责和装饰功能分开。
 * 而且可以去除相关类中重复的装饰逻辑
 *
 */
public class DecoratorMain {

    public static void main(String[] strg) {
        Person person = new Person("小菜");

        System.out.println("第一种装扮：");
        Sneaker sneaker = new Sneaker();
        BigTrousers bigTrousers = new BigTrousers();
        TShirts tShirts = new TShirts();
        sneaker.decorate(person);
        bigTrousers.decorate(sneaker);
        tShirts.decorate(bigTrousers);
        tShirts.show();

        System.out.println("第二种装扮：");
        LeatherShoes leatherShoes = new LeatherShoes();
        Tie tie = new Tie();
        Suit suit = new Suit();
        leatherShoes.decorate(person);
        tie.decorate(leatherShoes);
        suit.decorate(tie);
        suit.show();
    }
}
