package com.example.springboot.designMode.adapter;

/**
 *适配器模式
 * 将一个类的接口转换成客户希望的另一个接口。使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
 * 系统的数据和行为都正确，但接口不符时，应当考虑适配器，目的是使控制范围之外的一个原有对象与某个接口匹配
 *
 * 主要用于希望复用一些现存的类，但是接口又与复用环境要求不一致的情况
 */
public class AdapterMain {

    public static void main(String[] strg) {

        Player b = new Forwards("巴蒂尔");
        b.attack();

        //翻译者告诉姚明既要进攻也要防守
        Player ym = new Translator("姚明");
        ym.attack();
        ym.defense();
    }
}
