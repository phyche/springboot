package com.example.springboot.designMode.bridge;

public abstract class HandsetBrand {

    protected HandsetSoft handsetSoft;

    public HandsetSoft getHandsetSoft() {
        return handsetSoft;
    }

    public void setHandsetSoft(HandsetSoft handsetSoft) {
        this.handsetSoft = handsetSoft;
    }

    public abstract void run();
}
