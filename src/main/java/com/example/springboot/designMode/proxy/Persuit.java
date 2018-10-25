package com.example.springboot.designMode.proxy;

public class Persuit implements GiveGift {

    SchoolGirl schoolGirl;
    public Persuit(SchoolGirl schoolGirl) {
        this.schoolGirl = schoolGirl;
    }

    @Override
    public void giveDolls() {
        System.out.println(schoolGirl.getName() + "送你洋娃娃");
    }

    @Override
    public void giveFlowers() {
        System.out.println(schoolGirl.getName() + "送你鲜花");
    }

    @Override
    public void giveChocolate() {
        System.out.println(schoolGirl.getName() + "送你巧克力");
    }
}
