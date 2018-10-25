package com.example.springboot.designMode.adapter;

public class ForeignPlayer {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //表明外籍中锋只懂得中文进攻
    public void 进攻() {
        System.out.println("外籍中锋 " + name + " 进攻");
    }

    //表明外籍中锋只懂得中文防守
    public void 防守() {
        System.out.println("外籍中锋 " + name + " 防守");
    }
}
