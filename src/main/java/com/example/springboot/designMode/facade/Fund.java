package com.example.springboot.designMode.facade;

public class Fund {

    private Stock stock;
    private NationalDebate nationalDebate;
    private Realty realty;

    public Fund() {
        stock = new Stock();
        nationalDebate = new NationalDebate();
        realty = new Realty();
    }

    public void buyFund() {
        stock.buy();
        nationalDebate.buy();
        realty.buy();
    }

    public void sellFund() {
        stock.sell();
        nationalDebate.sell();
        realty.sell();
    }
}
