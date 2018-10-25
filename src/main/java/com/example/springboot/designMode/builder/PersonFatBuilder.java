package com.example.springboot.designMode.builder;

public class PersonFatBuilder extends PersonBuilder{

    @Override
    public void BuildHead() {
        System.out.println("画胖人的头");
    }

    @Override
    public void BuildBody() {
        System.out.println("画胖人的身体");
    }

    @Override
    public void BuildArmLeft() {
        System.out.println("画胖人的左手");
    }

    @Override
    public void BuildArmRight() {
        System.out.println("画胖人的右手");
    }

    @Override
    public void BuildLegLeft() {
        System.out.println("画胖人的左腿");
    }

    @Override
    public void BuildLegRight() {
        System.out.println("画胖人的右腿");
    }
}
