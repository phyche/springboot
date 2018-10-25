package com.example.springboot.designMode.adapter;

public class Translator extends Player {

    private ForeignPlayer foreignPlayer = new ForeignPlayer();
    public Translator(String name) {
        super(name);
        foreignPlayer.setName(name);
    }

    @Override
    public void attack() {
        foreignPlayer.进攻();//翻译者将attack翻译成进攻告诉外籍中锋
    }

    @Override
    public void defense() {
        foreignPlayer.防守();//翻译者将defense翻译成防守告诉外籍中锋
    }
}
