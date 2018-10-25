package com.example.springboot.designMode.bridge;

public class HandsetBrandA extends HandsetBrand {

    @Override
    public void run() {
        handsetSoft.run();
    }
}
