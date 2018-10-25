package com.example.springboot.designMode.bridge;

public class HandsetBrandB extends HandsetBrand {

    @Override
    public void run() {
        handsetSoft.run();
    }
}
