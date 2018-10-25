package com.example.springboot.designMode.proxy;

public class Proxy implements GiveGift {

    Persuit persuit;
    public Proxy (SchoolGirl schoolGirl) {
        persuit = new Persuit(schoolGirl);
    }

    @Override
    public void giveDolls() {
        persuit.giveDolls();
    }

    @Override
    public void giveFlowers() {
        persuit.giveFlowers();
    }

    @Override
    public void giveChocolate() {
        persuit.giveChocolate();
    }
}
