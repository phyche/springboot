package com.example.springboot.designMode.builder;

/**
 * 建造者模式
 * 将一个复杂对象的构建与表示分离，使得同样的构建过程有不同的表示
 *
 * 适用场合：在当创建复杂对象的算法应该独立于改对象的组成部分以及他们的装配方式时适用
 *
 * */
public class BuilderMain {

    public static void main(String[] strg) {

        PersonThinBuilder thinBuilder = new PersonThinBuilder();
        PersonDirector thinPerson = new PersonDirector(thinBuilder);
        System.out.println("开始画瘦人：");
        thinPerson.createPerson();

        PersonFatBuilder fatBuilder = new PersonFatBuilder();
        PersonDirector fatPerson = new PersonDirector(fatBuilder);
        System.out.println("开始画胖人：");
        fatPerson.createPerson();

    }
}
