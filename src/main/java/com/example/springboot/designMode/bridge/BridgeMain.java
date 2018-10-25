package com.example.springboot.designMode.bridge;

/**
 * 桥接模式
 *将抽象部分与它的实现部分分离，使他们都可以独立的变化
 *
 */
public class BridgeMain {

    public static void main(String[] strg) {

        HandsetBrand handsetBrand;
        handsetBrand = new HandsetBrandA();

        handsetBrand.setHandsetSoft(new HandsetGame());
        handsetBrand.run();

        handsetBrand.setHandsetSoft(new HandsetAddressList());
        handsetBrand.run();

        handsetBrand = new HandsetBrandB();
        handsetBrand.setHandsetSoft(new HandsetGame());
        handsetBrand.run();

        handsetBrand.setHandsetSoft(new HandsetAddressList());
        handsetBrand.run();

    }
}
